package com.androidinsight.core

import android.content.Context
import java.io.File
import java.util.zip.ZipFile
import org.jf.dexlib2.DexFileFactory
import org.jf.dexlib2.iface.ClassDef
import org.jf.dexlib2.Opcodes
import org.jf.baksmali.Adaptors.ClassDefinition
import org.jf.baksmali.BaksmaliOptions

class CodeAnalyzer(private val context: Context) {
    data class MethodSignature(
        val className: String,
        val methodName: String,
        val parameters: List<String>,
        val returnType: String,
        val accessFlags: Int,
        val isNative: Boolean,
        val controlFlowGraph: ControlFlowGraph? = null,
        val dataFlowAnalysis: DataFlowAnalysis? = null,
        val crossReferences: List<CrossReference> = emptyList()
    )

    data class ControlFlowGraph(
        val blocks: List<BasicBlock>,
        val edges: List<Edge>
    )

    data class BasicBlock(
        val id: Int,
        val instructions: List<String>,
        val startAddress: Long,
        val endAddress: Long
    )

    data class Edge(
        val from: Int,
        val to: Int,
        val type: EdgeType
    )

    enum class EdgeType {
        NORMAL, CONDITIONAL_TRUE, CONDITIONAL_FALSE, EXCEPTION
    }

    data class DataFlowAnalysis(
        val variableDefinitions: Map<String, List<String>>,
        val variableUses: Map<String, List<String>>,
        val taintSources: List<String>,
        val taintSinks: List<String>
    )

    data class CrossReference(
        val fromMethod: MethodSignature,
        val type: ReferenceType,
        val offset: Long
    )

    enum class ReferenceType {
        CALLS, CALLED_BY, READS, WRITTEN_BY
    }

    data class ApiCall(
        val className: String,
        val methodName: String,
        val lineNumber: Int
    )

    data class SecurityAnalysis(
        val hasAntiDebug: Boolean = false,
        val hasRootDetection: Boolean = false,
        val hasObfuscation: Boolean = false,
        val hasEmulator: Boolean = false,
        val hasTamperDetection: Boolean = false,
        val securityRisks: List<String> = emptyList(),
        val cryptoOperations: List<CryptoOperation> = emptyList(),
        val vulnerabilities: List<Vulnerability> = emptyList()
    )

    data class CryptoOperation(
        val type: String,
        val algorithm: String,
        val keySize: Int?,
        val isCustomImplementation: Boolean
    )

    data class Vulnerability(
        val type: VulnerabilityType,
        val severity: Severity,
        val description: String,
        val location: String,
        val recommendation: String
    )

    enum class VulnerabilityType {
        WEAK_CRYPTO, HARDCODED_KEY, INSECURE_RANDOM,
        BUFFER_OVERFLOW, FORMAT_STRING, USE_AFTER_FREE
    }

    enum class Severity {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    data class NativeLibrary(
        val name: String,
        val architecture: String,
        val size: Long,
        val isStripped: Boolean,
        val exportedFunctions: List<String>,
        val securityAnalysis: SecurityAnalysis,
        val symbolTable: Map<String, String>,
        val dynamicLinking: List<String>,
        val vulnerabilities: List<String>
    )

    fun analyzeDexFile(dexFile: File): List<MethodSignature> {
        val dexBackedDexFile = DexFileFactory.loadDexFile(dexFile, Opcodes.getDefault())
        return dexBackedDexFile.classes.flatMap { classDef ->
            classDef.methods.map { method ->
                val controlFlowGraph = generateControlFlowGraph(method)
                val dataFlowAnalysis = analyzeDataFlow(method)
                val crossRefs = findCrossReferences(dexBackedDexFile, classDef, method)
                
                MethodSignature(
                    className = classDef.type,
                    methodName = method.name,
                    parameters = method.parameters.map { it.type },
                    returnType = method.returnType,
                    accessFlags = method.accessFlags,
                    isNative = method.accessFlags and org.jf.dexlib2.AccessFlags.NATIVE.value != 0,
                    controlFlowGraph = controlFlowGraph,
                    dataFlowAnalysis = dataFlowAnalysis,
                    crossReferences = crossRefs
                )
            }
        }
    }

    private fun generateControlFlowGraph(method: org.jf.dexlib2.iface.Method): ControlFlowGraph {
        val blocks = mutableListOf<BasicBlock>()
        val edges = mutableListOf<Edge>()
        
        try {
            val instructions = method.implementation?.instructions ?: return ControlFlowGraph(blocks, edges)
            var currentBlock = mutableListOf<String>()
            var currentId = 0
            var currentAddress = 0L
            
            instructions.forEach { instruction ->
                if (isBlockBoundary(instruction)) {
                    if (currentBlock.isNotEmpty()) {
                        blocks.add(BasicBlock(
                            id = currentId++,
                            instructions = currentBlock.toList(),
                            startAddress = currentAddress,
                            endAddress = currentAddress + instruction.codeUnits
                        ))
                        currentBlock = mutableListOf()
                    }
                    
                    // Add edges based on instruction type
                    when {
                        instruction.opcode.name.startsWith("if") -> {
                            edges.add(Edge(currentId - 1, currentId, EdgeType.CONDITIONAL_TRUE))
                            edges.add(Edge(currentId - 1, currentId + 1, EdgeType.CONDITIONAL_FALSE))
                        }
                        instruction.opcode.name.startsWith("goto") -> {
                            edges.add(Edge(currentId - 1, currentId, EdgeType.NORMAL))
                        }
                    }
                }
                currentBlock.add(instruction.opcode.name)
                currentAddress += instruction.codeUnits
            }
        } catch (e: Exception) {
            // Log error but continue analysis
        }
        
        return ControlFlowGraph(blocks, edges)
    }

    private fun isBlockBoundary(instruction: org.jf.dexlib2.iface.instruction.Instruction): Boolean {
        return instruction.opcode.name.startsWith("if") ||
               instruction.opcode.name.startsWith("goto") ||
               instruction.opcode.name.startsWith("return") ||
               instruction.opcode.name.startsWith("throw")
    }

    private fun analyzeDataFlow(method: org.jf.dexlib2.iface.Method): DataFlowAnalysis {
        val varDefs = mutableMapOf<String, MutableList<String>>()
        val varUses = mutableMapOf<String, MutableList<String>>()
        val taintSources = mutableListOf<String>()
        val taintSinks = mutableListOf<String>()
        val dataFlows = mutableListOf<Pair<String, String>>()
        val taintTypes = mutableMapOf<String, String>() // Track type of sensitive data (e.g., "location", "imei")
        val taintPropagation = mutableListOf<Triple<String, String, String>>() // Track propagation path (source, sink, type)
        
        try {
            method.implementation?.instructions?.forEachIndexed { index, instruction ->
                when (instruction.opcode.name) {
                    "iget", "sget" -> {
                        // Track field reads
                        val fieldRef = (instruction as? org.jf.dexlib2.iface.instruction.ReferenceInstruction)?.reference
                        fieldRef?.let { ref ->
                            val fieldName = (ref as? org.jf.dexlib2.iface.reference.FieldReference)?.name ?: return@let
                            varUses.getOrPut(fieldName) { mutableListOf() }.add(instruction.opcode.name)
                            if (isTaintSource(fieldName)) {
                                taintSources.add(fieldName)
                                trackDataFlow(fieldName, method, index, dataFlows)
                            }
                        }
                    }
                    "iput", "sput" -> {
                        // Track field writes
                        val fieldRef = (instruction as? org.jf.dexlib2.iface.instruction.ReferenceInstruction)?.reference
                        fieldRef?.let { ref ->
                            val fieldName = (ref as? org.jf.dexlib2.iface.reference.FieldReference)?.name ?: return@let
                            varDefs.getOrPut(fieldName) { mutableListOf() }.add(instruction.opcode.name)
                            if (isTaintSink(fieldName)) {
                                taintSinks.add(fieldName)
                                checkTaintFlow(fieldName, taintSources, dataFlows)
                            }
                        }
                    }
                    "invoke-virtual", "invoke-direct" -> {
                        // Track method calls that might be taint sources/sinks
                        val methodRef = (instruction as? org.jf.dexlib2.iface.instruction.ReferenceInstruction)?.reference
                        methodRef?.let { ref ->
                            val methodName = (ref as? org.jf.dexlib2.iface.reference.MethodReference)?.name ?: return@let
                            if (isTaintSource(methodName)) {
                                taintSources.add(methodName)
                                trackDataFlow(methodName, method, index, dataFlows)
                            }
                            if (isTaintSink(methodName)) {
                                taintSinks.add(methodName)
                                checkTaintFlow(methodName, taintSources, dataFlows)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Log error but continue analysis
        }
        
        return DataFlowAnalysis(
            variableDefinitions = varDefs,
            variableUses = varUses,
            taintSources = taintSources,
            taintSinks = taintSinks,
            dataFlows = dataFlows,
            taintTypes = taintTypes,
            taintPropagation = taintPropagation
        )
    }

    private fun trackDataFlow(source: String, method: org.jf.dexlib2.iface.Method, startIndex: Int, dataFlows: MutableList<Pair<String, String>>) {
        // Implement data flow tracking logic
        // This is a simplified placeholder implementation
        method.implementation?.instructions?.drop(startIndex)?.forEach { instruction ->
            when (instruction.opcode.name) {
                "move-result", "move-result-object" -> {
                    val destination = (instruction as? org.jf.dexlib2.iface.instruction.OneRegisterInstruction)?.registerA?.toString() ?: return@forEach
                    dataFlows.add(Pair(source, destination))
                }
            }
        }
    }

    private fun checkTaintFlow(sink: String, sources: List<String>, dataFlows: List<Pair<String, String>>) {
        // Enhanced taint flow analysis with type propagation
        sources.forEach { source ->
            if (dataFlows.any { it.first == source && it.second == sink }) {
                val sourceType = taintTypes[source] ?: "unknown"
                val sinkType = taintTypes[sink] ?: "unknown"
                taintPropagation.add(Triple(source, sink, "$sourceType->$sinkType"))
                println("Potential taint flow detected: ${sourceType.uppercase()} data from $source " +
                        "flows to ${sinkType.uppercase()} sink at $sink")
            }
        }
    }

    private fun isTaintSource(name: String): Boolean {
        // Enhanced taint source detection with type categorization
        return when {
            name.contains("getDeviceId") || name.contains("getImei") -> {
                taintTypes[name] = "device_identifier"
                true
            }
            name.contains("getLocation") || name.contains("getLastKnownLocation") -> {
                taintTypes[name] = "location"
                true
            }
            name.contains("getAccounts") || name.contains("getContacts") -> {
                taintTypes[name] = "personal_data"
                true
            }
            name.contains("getClipboard") || name.contains("getPrimaryClip") -> {
                taintTypes[name] = "clipboard"
                true
            }
            else -> false
        }
    }

    private fun isTaintSink(name: String): Boolean {
        // Enhanced taint sink detection with type categorization
        return when {
            name.contains("sendHttp") || name.contains("post") || name.contains("upload") -> {
                taintTypes[name] = "network"
                true
            }
            name.contains("writeFile") || name.contains("save") || name.contains("store") -> {
                taintTypes[name] = "storage"
                true
            }
            name.contains("sendSms") || name.contains("sendText") -> {
                taintTypes[name] = "sms"
                true
            }
            name.contains("exec") || name.contains("Runtime.getRuntime") -> {
                taintTypes[name] = "command_execution"
                true
            }
            else -> false
        }
    }

    private fun findCrossReferences(
        dexFile: org.jf.dexlib2.iface.DexFile,
        classDef: org.jf.dexlib2.iface.ClassDef,
        method: org.jf.dexlib2.iface.Method
    ): List<CrossReference> {
        val crossRefs = mutableListOf<CrossReference>()
        
        try {
            // Find methods that call this method
            dexFile.classes.forEach { otherClass ->
                otherClass.methods.forEach { otherMethod ->
                    otherMethod.implementation?.instructions?.forEachIndexed { index, instruction ->
                        if (instruction is org.jf.dexlib2.iface.instruction.ReferenceInstruction) {
                            val reference = instruction.reference
                            if (reference is org.jf.dexlib2.iface.reference.MethodReference) {
                                if (reference.definingClass == classDef.type &&
                                    reference.name == method.name &&
                                    reference.parameterTypes == method.parameters.map { it.type }) {
                                    crossRefs.add(CrossReference(
                                        fromMethod = MethodSignature(
                                            className = otherClass.type,
                                            methodName = otherMethod.name,
                                            parameters = otherMethod.parameters.map { it.type },
                                            returnType = otherMethod.returnType,
                                            accessFlags = otherMethod.accessFlags,
                                            isNative = otherMethod.accessFlags and org.jf.dexlib2.AccessFlags.NATIVE.value != 0
                                        ),
                                        type = ReferenceType.CALLS,
                                        offset = index.toLong()
                                    ))
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Log error but continue analysis
        }
        
        return crossRefs
    }

    fun analyzeNativeLibraries(apkFile: File): List<NativeLibrary> {
        val nativeLibs = mutableListOf<NativeLibrary>()
        ZipFile(apkFile).use { zip ->
            zip.entries().asSequence()
                .filter { it.name.startsWith("lib/") && it.name.endsWith(".so") }
                .forEach { entry ->
                    val arch = entry.name.split("/")[1]
                    val libName = entry.name.split("/").last()
                    val tempFile = File.createTempFile(libName, ".so")
                    tempFile.deleteOnExit()
                    zip.getInputStream(entry).use { input ->
                        tempFile.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                    val symbolTable = parseSymbolTable(tempFile)
                    val dynamicLinking = analyzeDynamicLinking(tempFile)
                    val vulnerabilities = detectVulnerabilities(tempFile)
                    val securityAnalysis = performSecurityAnalysis(tempFile)
                    nativeLibs.add(NativeLibrary(
                        name = libName,
                        architecture = arch,
                        size = entry.size,
                        isStripped = isStripped(tempFile),
                        exportedFunctions = symbolTable.filter { it.value == "FUNC" }.keys.toList(),
                        securityAnalysis = securityAnalysis,
                        symbolTable = symbolTable,
                        dynamicLinking = dynamicLinking,
                        vulnerabilities = vulnerabilities
                    ))
                    tempFile.delete()
                }
        }
        return nativeLibs
    }

    private fun parseSymbolTable(libFile: File): Map<String, String> {
        val symbolTable = mutableMapOf<String, String>()
        
        // Enhanced ELF parsing with detailed symbol information and security analysis
        val process = ProcessBuilder("readelf", "-s", "--wide", "--dyn-syms", libFile.absolutePath).start()
        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                if (line.trim().isNotEmpty() && !line.startsWith("Num:")) {
                    val parts = line.trim().split("\\s+".toRegex())
                    if (parts.size >= 8) {
                        val symbolName = parts[7]
                        val symbolType = when (parts[3]) {
                            "FUNC" -> "FUNCTION"
                            "OBJECT" -> "OBJECT"
                            "NOTYPE" -> "UNDEFINED"
                            else -> parts[3]
                        }
                        val symbolSize = parts[2].toLongOrNull() ?: 0L
                        val symbolValue = parts[1].toLongOrNull(16) ?: 0L
                        val symbolBind = parts[4]
                        val symbolVis = parts[5]
                        val section = parts[6]
                        
                        if (symbolName.isNotEmpty()) {
                            // Enhanced symbol information with security flags
                            val securityFlags = mutableListOf<String>()
                            if (symbolName.contains("debug")) securityFlags.add("DEBUG")
                            if (symbolName.contains("anti")) securityFlags.add("ANTI_TAMPER")
                            if (symbolName.contains("root")) securityFlags.add("ROOT_DETECT")
                            if (symbolName.contains("frida")) securityFlags.add("FRIDA_DETECT")
                            if (symbolName.contains("ptrace")) securityFlags.add("PTRACE_DETECT")
                            if (symbolName.contains("substrate")) securityFlags.add("SUBSTRATE_DETECT")
                            
                            // Detect memory corruption vulnerabilities
                            if (symbolName.contains("memcpy") || symbolName.contains("strcpy")) {
                                securityFlags.add("MEMORY_OPERATION")
                            }
                            
                            // Detect crypto operations
                            if (symbolName.contains("AES") || symbolName.contains("DES") || 
                                symbolName.contains("RSA") || symbolName.contains("SHA")) {
                                securityFlags.add("CRYPTO_OPERATION")
                            }
                            
                            // Detect vulnerable functions
                            if (symbolName.contains("gets") || symbolName.contains("sprintf") ||
                                symbolName.contains("strcat") || symbolName.contains("vsprintf")) {
                                securityFlags.add("VULNERABLE_FUNCTION")
                            }
                            
                            symbolTable[symbolName] = "$symbolType|$symbolSize|$symbolValue|$symbolBind|$symbolVis|$section" +
                                if (securityFlags.isNotEmpty()) "|${securityFlags.joinToString(",")}" else ""
                        }
                    }
                }
            }
        }
        
        // Add section headers analysis
        val sectionProcess = ProcessBuilder("readelf", "-S", "--wide", libFile.absolutePath).start()
        sectionProcess.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                if (line.contains(" ") && !line.startsWith("Section Headers:") && !line.startsWith("  [Nr]")) {
                    val parts = line.trim().split("\\s+".toRegex())
                    if (parts.size >= 7) {
                        val sectionName = parts[1]
                        val sectionType = parts[2]
                        val sectionAddr = parts[3]
                        val sectionSize = parts[5]
                        
                        if (sectionName.isNotEmpty() && !symbolTable.containsKey(sectionName)) {
                            symbolTable[sectionName] = "SECTION|$sectionSize|$sectionAddr|$sectionType"
                        }
                    }
                }
            }
        }
        
        return symbolTable
    }

    private fun analyzeDynamicLinking(libFile: File): List<String> {
        val dynamicLinking = mutableListOf<String>()
        val process = ProcessBuilder("readelf", "-d", "--wide", libFile.absolutePath).start()
        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                if (line.contains("(NEEDED)")) {
                    val libName = line.substringAfter("]").trim()
                            .removeSurrounding("[", "]")
                    
                    // Check for security-relevant libraries
                    if (libName.contains("frida") || libName.contains("xposed") || 
                        libName.contains("substrate") || libName.contains("debug")) {
                        dynamicLinking.add("SECURITY:$libName")
                    } else {
                        dynamicLinking.add(libName)
                    }
                } else if (line.contains("FLAGS")) {
                    // Check for security-relevant flags
                    val flags = line.substringAfter("FLAGS").trim()
                    if (flags.contains("BIND_NOW")) dynamicLinking.add("SECURITY_FLAG:RELRO")
                    if (flags.contains("PIE")) dynamicLinking.add("SECURITY_FLAG:PIE")
                }
                }
            }
        }
        
        // Also analyze symbol dependencies
        val symbolProcess = ProcessBuilder("readelf", "-s", "--wide", "--dyn-syms", libFile.absolutePath).start()
        symbolProcess.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                if (line.contains("UND")) {
                    val parts = line.trim().split("\\s+".toRegex())
                    if (parts.size >= 8 && parts[4] == "UND") {
                        val symbol = parts[7]
                        if (symbol.isNotEmpty() && !symbol.startsWith("_") && !dynamicLinking.contains(symbol)) {
                            dynamicLinking.add(symbol)
                        }
                    }
                }
            }
        }
        
        return dynamicLinking.distinct()
    }

    private fun detectVulnerabilities(libFile: File): List<String> {
        val vulnerabilities = mutableListOf<String>()
        
        // Enhanced vulnerability detection with anti-debugging checks
        val antiDebugPatterns = listOf(
            "ptrace", "fopen", "/proc/self/status", "/proc/self/maps",
            "getpid", "gettid", "kill", "fork", "syscall",
            "inotify", "frida", "xposed", "substrate"
        )
        
        // Check for memory corruption vulnerabilities
        val memProcess = ProcessBuilder("objdump", "-d", "--no-show-raw-insn", "--disassemble-all", libFile.absolutePath).start()
        memProcess.inputStream.bufferedReader().useLines { lines ->
            var currentFunction = ""
            lines.forEach { line ->
                if (line.startsWith("00000000")) {
                    currentFunction = line.substringAfter("<").substringBefore(">")
                }
                
                when {
                    line.contains("strcpy") || line.contains("strcat") -> 
                        vulnerabilities.add("Buffer overflow in $currentFunction: Use of unsafe string function")
                    line.contains("system") || line.contains("popen") -> 
                        vulnerabilities.add("Command injection in $currentFunction: Use of system()/popen()")
                    line.contains("gets") -> 
                        vulnerabilities.add("Buffer overflow in $currentFunction: Use of gets()")
                    line.contains("strtok") -> 
                        vulnerabilities.add("Potential TOCTOU in $currentFunction: Use of strtok()")
                    line.contains("rand") || line.contains("srand") -> 
                        vulnerabilities.add("Weak randomness in $currentFunction: Use of rand()/srand()")
                    line.contains("memcpy") && line.contains(", [esp]") -> 
                        vulnerabilities.add("Potential stack overflow in $currentFunction: memcpy with stack source")
                    antiDebugPatterns.any { pattern -> line.contains(pattern) } ->
                        vulnerabilities.add("Anti-debugging technique detected in $currentFunction: ${antiDebugPatterns.first { line.contains(it) }}")
                }
            }
        }
        
        // Enhanced format string vulnerability detection
        val fmtProcess = ProcessBuilder("objdump", "-d", "--no-show-raw-insn", libFile.absolutePath).start()
        fmtProcess.inputStream.bufferedReader().useLines { lines ->
            var currentFunction = ""
            lines.forEach { line ->
                if (line.startsWith("00000000")) {
                    currentFunction = line.substringAfter("<").substringBefore(">")
                }
                
                when {
                    line.contains("printf") && !line.contains("__printf_chk") ->
                        vulnerabilities.add("Format string vulnerability in $currentFunction: Unprotected printf found")
                }
            }
        }
        
        // Check for insecure crypto
        val cryptoProcess = ProcessBuilder("strings", libFile.absolutePath).start()
        cryptoProcess.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                when {
                    line.contains("DES") || line.contains("RC4") || line.contains("MD5") -> 
                        vulnerabilities.add("Weak crypto: Use of ${line.trim()} algorithm")
                    line.contains("AES/ECB") -> 
                        vulnerabilities.add("Insecure mode: AES in ECB mode")
                    line.contains("RSA/None/NoPadding") -> 
                        vulnerabilities.add("Insecure padding: RSA with NoPadding")
                }
            }
        }
        
        return vulnerabilities.distinct()
    }

    private fun performSecurityAnalysis(libFile: File): SecurityAnalysis {
        val hasAntiDebug = detectAntiDebugTechniques(libFile)
        val hasRootDetection = detectRootDetection(libFile)
        val hasObfuscation = detectObfuscation(libFile)
        val hasEmulator = detectEmulator(libFile)
        val hasTamperDetection = detectTamperProtection(libFile)
        val cryptoOps = analyzeCryptoOperations(libFile)
        val vulnerabilities = detectVulnerabilities(libFile)
        
        return SecurityAnalysis(
            hasAntiDebug = hasAntiDebug,
            hasRootDetection = hasRootDetection,
            hasObfuscation = hasObfuscation,
            hasEmulator = hasEmulator,
            hasTamperDetection = hasTamperDetection,
            securityRisks = vulnerabilities.map { it.type.name },
            cryptoOperations = cryptoOps,
            vulnerabilities = vulnerabilities
        )
    }
    
    private fun detectAntiDebugTechniques(libFile: File): Boolean {
        val process = ProcessBuilder("objdump", "-d", "--no-show-raw-insn", libFile.absolutePath).start()
        return process.inputStream.bufferedReader().useLines { lines ->
            lines.any { line ->
                line.contains("ptrace") || 
                line.contains("fopen") && line.contains("/proc/self/status") ||
                line.contains("getppid") ||
                line.contains("kill") && line.contains("0")
            }
        }
    }
    
    private fun detectRootDetection(libFile: File): Boolean {
        val process = ProcessBuilder("strings", libFile.absolutePath).start()
        return process.inputStream.bufferedReader().useLines { lines ->
            lines.any { line ->
                line.contains("/su") || 
                line.contains("/superuser") ||
                line.contains("/magisk") ||
                line.contains("/xbin") ||
                line.contains("/system/bin") ||
                line.contains("/system/xbin")
            }
        }
    }
    
    private fun detectObfuscation(libFile: File): Boolean {
        val process = ProcessBuilder("readelf", "-s", "--wide", libFile.absolutePath).start()
        return process.inputStream.bufferedReader().useLines { lines ->
            lines.filter { it.contains("FUNC") }
                .count { line ->
                    val symbol = line.trim().split("\\s+".toRegex()).last()
                    symbol.length <= 2 || symbol.matches("[a-f0-9]+".toRegex())
                } > 10
        }
    }
    
    private fun detectEmulator(libFile: File): Boolean {
        val process = ProcessBuilder("strings", libFile.absolutePath).start()
        return process.inputStream.bufferedReader().useLines { lines ->
            lines.any { line ->
                line.contains("emulator") || 
                line.contains("qemu") ||
                line.contains("goldfish") ||
                line.contains("android_x86")
            }
        }
    }
    
    private fun detectTamperProtection(libFile: File): Boolean {
        val process = ProcessBuilder("objdump", "-d", "--no-show-raw-insn", libFile.absolutePath).start()
        return process.inputStream.bufferedReader().useLines { lines ->
            lines.any { line ->
                line.contains("signature") || 
                line.contains("checksum") ||
                line.contains("integrity") ||
                line.contains("verify") && line.contains("apk")
            }
        }
    }
    
    private fun analyzeCryptoOperations(libFile: File): List<CryptoOperation> {
        val cryptoOps = mutableListOf<CryptoOperation>()
        val process = ProcessBuilder("strings", libFile.absolutePath).start()
        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                when {
                    line.contains("AES/") -> cryptoOps.add(CryptoOperation("AES", line.substringAfter("AES/"), 128, false))
                    line.contains("DES/") -> cryptoOps.add(CryptoOperation("DES", line.substringAfter("DES/"), 56, false))
                    line.contains("RSA/") -> cryptoOps.add(CryptoOperation("RSA", line.substringAfter("RSA/"), 2048, false))
                    line.contains("SHA") -> cryptoOps.add(CryptoOperation("Hash", line, null, false))
                    line.contains("MD5") -> cryptoOps.add(CryptoOperation("Hash", "MD5", null, false))
                }
            }
        }
        return cryptoOps
    }
    
    private fun isLibraryObfuscated(libFile: File): Boolean {
        val process = ProcessBuilder("strings", "-a", libFile.absolutePath).start()
        var meaningfulStrings = 0
        var totalStrings = 0
        
        process.inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                totalStrings++
                if (line.length > 4 && line.matches(Regex("[a-zA-Z0-9_]+"))) {
                    meaningfulStrings++
                }
            }
        }
        
        // If less than 10% of strings are meaningful, likely obfuscated
        return meaningfulStrings.toFloat() / totalStrings < 0.1f
    }

    private fun isStripped(libFile: File): Boolean {
        val process = ProcessBuilder("readelf", "-s", libFile.absolutePath).start()
        return process.inputStream.bufferedReader().useLines { lines ->
            !lines.any { it.contains("Symbol table") }
        }
    }

    fun analyzeNativeLibraries(apkFile: File): List<NativeLibrary> {
        val nativeLibs = mutableListOf<NativeLibrary>()
        ZipFile(apkFile).use { zip ->
            zip.entries().asSequence()
                .filter { it.name.startsWith("lib/") && it.name.endsWith(".so") }
                .forEach { entry ->
                    val arch = entry.name.split("/")[1]
                    val libName = entry.name.split("/").last()
                    
                    // Read library content for analysis
                    val libContent = zip.getInputStream(entry).readBytes()
                    val isStripped = isLibraryStripped(libContent)
                    val exportedFunctions = extractExportedFunctions(libContent)
                    val securityAnalysis = analyzeLibrarySecurity(libContent)
                    val symbolTable = parseSymbolTable(libContent)
                    val dynamicLinking = analyzeDynamicLinking(libContent)
                    val vulnerabilities = detectVulnerabilities(libContent)

                    nativeLibs.add(NativeLibrary(
                        name = libName,
                        architecture = arch,
                        size = entry.size,
                        isStripped = isStripped,
                        exportedFunctions = exportedFunctions,
                        securityAnalysis = securityAnalysis,
                        symbolTable = symbolTable,
                        dynamicLinking = dynamicLinking,
                        vulnerabilities = vulnerabilities
                    ))
                }
        }
        return nativeLibs
    }

    private fun parseSymbolTable(content: ByteArray): Map<String, String> {
        val symbolTable = mutableMapOf<String, String>()
        val elfHeader = ByteBuffer.wrap(content).order(ByteOrder.LITTLE_ENDIAN)
    
        // Check ELF magic number
        if (!content.startsWith(byteArrayOf(0x7f, 'E'.toByte(), 'L'.toByte(), 'F'.toByte()))) {
            return symbolTable
        }
    
        // Parse ELF header
        val is64Bit = elfHeader.get(4) == 2.toByte()
        val sectionHeaderOffset = if (is64Bit) elfHeader.getLong(40) else elfHeader.getInt(32).toLong()
        val sectionHeaderEntrySize = elfHeader.getShort(if (is64Bit) 58 else 46).toInt()
        val sectionHeaderCount = elfHeader.getShort(if (is64Bit) 60 else 48).toInt()
    
        // Check for RELRO
        val hasFullRELRO = checkFullRELRO(elfHeader, sectionHeaderOffset, sectionHeaderEntrySize, sectionHeaderCount)
        if (!hasFullRELRO) {
            vulnerabilities.add("No FULL RELRO: The binary is not fully protected against GOT overwrite attacks")
        }
    
        // Check for NX bit
        val hasNXBit = checkNXBit(elfHeader)
        if (!hasNXBit) {
            vulnerabilities.add("No NX bit: The stack is executable, which can be exploited for code injection attacks")
        }
    
        // Check for stack canaries
        val hasStackCanaries = checkStackCanaries(content)
        if (!hasStackCanaries) {
            vulnerabilities.add("No stack canaries: The binary is not protected against stack buffer overflow attacks")
        }
    
        // Check for suspicious functions
        val suspiciousFunctions = listOf("strcpy", "strcat", "sprintf", "gets")
        val symbolTable = parseSymbolTable(content)
        suspiciousFunctions.forEach { func ->
            if (symbolTable.containsKey(func)) {
                vulnerabilities.add("Use of potentially unsafe function: $func")
            }
        }
    
        return vulnerabilities
    }

    private fun checkFullRELRO(elfHeader: ByteBuffer, sectionHeaderOffset: Long, sectionHeaderEntrySize: Int, sectionHeaderCount: Int): Boolean {
        for (i in 0 until sectionHeaderCount) {
            val sectionHeaderOffset = sectionHeaderOffset + i * sectionHeaderEntrySize
            val sectionType = elfHeader.getInt(sectionHeaderOffset.toInt() + 4)
            if (sectionType == 7) { // PT_GNU_RELRO
                return true
            }
        }
        return false
    }

    private fun checkNXBit(elfHeader: ByteBuffer): Boolean {
        val e_flags = elfHeader.getInt(36)
        return (e_flags and 0x2) != 0 // PF_X flag
    }

    private fun checkStackCanaries(content: ByteArray): Boolean {
        return content.contains("__stack_chk_fail".toByteArray())
    }

    private fun parseExportTable(content: ByteArray, offset: Long): List<String> {
        // Parse export table entries
        return emptyList()
    }

    private fun analyzeCryptoOperations(content: ByteArray): List<CryptoOperation> {
        val operations = mutableListOf<CryptoOperation>()
        
        // Common crypto algorithm patterns with security assessment
        val cryptoPatterns = mapOf(
            "AES" to CryptoPattern("AES", isWeak = false, minKeySize = 128),
            "DES" to CryptoPattern("DES", isWeak = true, minKeySize = 56),
            "3DES" to CryptoPattern("3DES", isWeak = true, minKeySize = 168),
            "RC4" to CryptoPattern("RC4", isWeak = true, minKeySize = 40),
            "RC2" to CryptoPattern("RC2", isWeak = true, minKeySize = 40),
            "Blowfish" to CryptoPattern("Blowfish", isWeak = false, minKeySize = 32),
            "SHA1" to CryptoPattern("SHA1", isWeak = true, isHash = true),
            "MD5" to CryptoPattern("MD5", isWeak = true, isHash = true),
            "SHA256" to CryptoPattern("SHA256", isWeak = false, isHash = true),
            "SHA512" to CryptoPattern("SHA512", isWeak = false, isHash = true),
            "RSA" to CryptoPattern("RSA", isWeak = false, minKeySize = 2048),
            "ECC" to CryptoPattern("ECC", isWeak = false, minKeySize = 256),
            "DSA" to CryptoPattern("DSA", isWeak = false, minKeySize = 2048),
            "HMAC" to CryptoPattern("HMAC", isWeak = false, isHash = true)
        )
        
        data class CryptoPattern(
            val name: String,
            val isWeak: Boolean,
            val minKeySize: Int = 0,
            val isHash: Boolean = false
        )
        
        cryptoPatterns.forEach { (pattern, algorithm) ->
            if (content.contains(pattern.toByteArray())) {
                operations.add(CryptoOperation(
                    type = "Encryption",
                    algorithm = algorithm,
                    keySize = detectKeySize(content, algorithm),
                    isCustomImplementation = isCustomCryptoImplementation(content, algorithm)
                ))
            }
        }
        
        return operations
    }

    private fun detectKeySize(content: ByteArray, algorithm: String): Int? {
        // Attempt to detect key size from binary patterns
        return null
    }

    private fun isCustomCryptoImplementation(content: ByteArray, algorithm: String): Boolean {
        // Check for standard crypto library usage vs custom implementation
        val standardLibraries = arrayOf(
            "openssl", "boringssl", "mbedtls",
            "libcrypto", "wolfssl", "gcrypt"
        )
        
        return !standardLibraries.any { lib ->
            content.contains(lib.toByteArray())
        }
    }
    
    private fun analyzeMemoryCorruption(content: ByteArray): List<Vulnerability> {
        val vulnerabilities = mutableListOf<Vulnerability>()
        
        // Check for dangerous function usage and memory patterns
        val dangerousFunctions = mapOf(
            "strcpy" to "Buffer overflow risk in strcpy",
            "strcat" to "Buffer overflow risk in strcat",
            "gets" to "Buffer overflow risk in gets",
            "sprintf" to "Format string vulnerability in sprintf",
            "scanf" to "Buffer overflow risk in scanf",
            "memcpy" to "Potential buffer overflow in memcpy",
            "memmove" to "Potential buffer overflow in memmove",
            "malloc" to "Dynamic memory allocation without bounds check",
            "realloc" to "Memory reallocation without size validation",
            "free" to "Potential use-after-free vulnerability",
            "alloca" to "Stack-based buffer allocation risk",
            "printf" to "Format string vulnerability in printf",
            "vsprintf" to "Buffer overflow risk in vsprintf",
            "strncat" to "Potential buffer overflow in strncat",
            "strncpy" to "Potential buffer overflow in strncpy",
            "read" to "Unbounded read operation",
            "write" to "Unbounded write operation",
            "system" to "Command injection vulnerability",
            "exec" to "Command execution vulnerability",
            "popen" to "Process injection vulnerability"
        )
        
        dangerousFunctions.forEach { (function, description) ->
            if (content.contains(function.toByteArray())) {
                vulnerabilities.add(Vulnerability(
                    type = VulnerabilityType.BUFFER_OVERFLOW,
                    severity = Severity.HIGH,
                    description = description,
                    location = "Native library",
                    recommendation = "Use secure alternatives like strncpy, strlcpy, or snprintf"
                ))
            }
        }
        
        return vulnerabilities
    }

    private fun detectMemoryCorruption(content: ByteArray): Vulnerability? {
        val memoryCorruptionPatterns = listOf(
            "memcpy" to "Unsafe memory copy operations",
            "memmove" to "Unsafe memory move operations",
            "malloc" to "Dynamic memory allocation without bounds check",
            "realloc" to "Memory reallocation without size validation"
        )
        
        for ((pattern, description) in memoryCorruptionPatterns) {
            if (content.contains(pattern.toByteArray())) {
                return Vulnerability(
                    type = VulnerabilityType.BUFFER_OVERFLOW,
                    severity = Severity.HIGH,
                    description = "Memory corruption risk: $description",
                    location = "Native library",
                    recommendation = "Implement proper bounds checking and use safer memory management functions"
                )
            }
        }
        
        return null
    }
    
    private fun detectBufferOverflow(content: ByteArray): Vulnerability? {
        val bufferOverflowPatterns = listOf(
            "strcpy" to "Use of unsafe string copy function",
            "strcat" to "Use of unsafe string concatenation function",
            "gets" to "Use of unsafe input function",
            "sprintf" to "Use of unsafe string formatting function",
            "scanf" to "Use of unsafe input parsing function",
            "vsprintf" to "Use of unsafe variable string formatting function"
        )
        
        for ((pattern, description) in bufferOverflowPatterns) {
            if (content.contains(pattern.toByteArray())) {
                return Vulnerability(
                    type = VulnerabilityType.BUFFER_OVERFLOW,
                    severity = Severity.HIGH,
                    description = "Buffer overflow risk: $description",
                    location = "Native library",
                    recommendation = "Use secure alternatives like strncpy, strlcpy, or snprintf with proper bounds checking"
                )
            }
        }
        
        return null
    }
    
    private fun detectUseAfterFree(content: ByteArray): Vulnerability? {
        // Check for patterns that might indicate use-after-free vulnerabilities
        if (content.contains("free".toByteArray())) {
            // This is a simplified check - in a real implementation, we would need more sophisticated analysis
            return Vulnerability(
                type = VulnerabilityType.USE_AFTER_FREE,
                severity = Severity.HIGH,
                description = "Potential use-after-free vulnerability detected",
                location = "Native library",
                recommendation = "Implement proper memory management and nullify pointers after freeing memory"
            )
        }
        
        return null
    }
    
            "malloc" to "Dynamic memory allocation without bounds check",
            "realloc" to "Memory reallocation without size validation"
        )
        
        for ((pattern, description) in memoryCorruptionPatterns) {
            if (content.contains(pattern.toByteArray())) {
                return Vulnerability(
                    type = VulnerabilityType.BUFFER_OVERFLOW,
                    severity = Severity.HIGH,
                    description = "Memory corruption risk: $description",
                    location = "Native library",
                    recommendation = "Implement proper bounds checking and use safer memory management functions"
                )
            }
        }
        
        return null
    }
    
    private fun detectBufferOverflow(content: ByteArray): Vulnerability? {
        val bufferOverflowPatterns = listOf(
            "strcpy" to "Use of unsafe string copy function",
            "strcat" to "Use of unsafe string concatenation function",
            "gets" to "Use of unsafe input function",
            "sprintf" to "Use of unsafe string formatting function",
            "scanf" to "Use of unsafe input parsing function",
            "vsprintf" to "Use of unsafe variable string formatting function"
        )
        
        for ((pattern, description) in bufferOverflowPatterns) {
            if (content.contains(pattern.toByteArray())) {
                return Vulnerability(
                    type = VulnerabilityType.BUFFER_OVERFLOW,
                    severity = Severity.HIGH,
                    description = "Buffer overflow risk: $description",
                    location = "Native library",
                    recommendation = "Use secure alternatives like strncpy, strlcpy, or snprintf with proper bounds checking"
                )
            }
        }
        
        return null
    }
    
    private fun detectUseAfterFree(content: ByteArray): Vulnerability? {
        // Check for patterns that might indicate use-after-free vulnerabilities
        if (content.contains("free".toByteArray())) {
            // This is a simplified check - in a real implementation, we would need more sophisticated analysis
            return Vulnerability(
                type = VulnerabilityType.USE_AFTER_FREE,
                severity = Severity.HIGH,
                description = "Potential use-after-free vulnerability detected",
                location = "Native library",
                recommendation = "Implement proper memory management and nullify pointers after freeing memory"
            )
        }
        
        return null
    }
    
    private fun analyzeLibrarySecurity(content: ByteArray): SecurityAnalysis {
        val securityRisks = mutableListOf<String>()
        val cryptoOps = mutableListOf<CryptoOperation>()
        val vulnerabilities = mutableListOf<Vulnerability>()

        // Memory corruption vulnerability detection
        detectMemoryCorruption(content)?.let { vulnerabilities.add(it) }
        detectBufferOverflow(content)?.let { vulnerabilities.add(it) }
        detectUseAfterFree(content)?.let { vulnerabilities.add(it) }

        // Crypto analysis
        analyzeCryptoOperations(content).forEach { 
            cryptoOps.add(it)
            if (it.isCustomImplementation || it.keySize?.let { size -> size < 128 } == true) {
                vulnerabilities.add(Vulnerability(
                    type = VulnerabilityType.WEAK_CRYPTO,
                    severity = Severity.HIGH,
                    description = "Weak or custom cryptographic implementation detected",
                    location = "Crypto operation: ${it.algorithm}",
                    recommendation = "Use standard cryptographic libraries with appropriate key sizes"
                ))
            }
        }

        // Advanced security checks
        return SecurityAnalysis(
            hasAntiDebug = detectAntiDebugTechniques(content),
            hasRootDetection = detectRootDetection(content),
            hasEmulator = detectEmulator(content),
            hasObfuscation = detectObfuscation(content),
            hasTamperDetection = detectTamperProtection(content),
            securityRisks = securityRisks,
            cryptoOperations = cryptoOps,
            vulnerabilities = vulnerabilities
        )
    }

    fun decompileMethod(dexFile: File, methodSignature: MethodSignature): String {
        val dexBackedDexFile = DexFileFactory.loadDexFile(dexFile, Opcodes.getDefault())
        BaksmaliOptions()
        
        val targetClass = dexBackedDexFile.classes.find { 
            it.type == methodSignature.className 
        }
        
        return targetClass?.let { classDef ->
            val method = classDef.methods.find { 
                it.name == methodSignature.methodName &&
                it.parameters.map { param -> param.type } == methodSignature.parameters &&
                it.returnType == methodSignature.returnType
            }
            
            method?.let {
                // TODO: Implement proper method decompilation using baksmali
                "// Decompiled method ${methodSignature.methodName}"
            } ?: "Method not found"
        } ?: "Class not found"
    }
}


private fun detectVulnerabilities(content: ByteArray): List<String> {
    val vulnerabilities = mutableListOf<String>()

    // Check for buffer overflow vulnerabilities
    val bufferOverflowFunctions = listOf("strcpy", "strcat", "gets", "sprintf")
    bufferOverflowFunctions.forEach { func ->
        if (content.contains(func.toByteArray())) {
            vulnerabilities.add("Potential buffer overflow vulnerability: Use of unsafe function $func")
        }
    }

    // Check for format string vulnerabilities
    val formatStringFunctions = listOf("printf", "fprintf", "sprintf", "snprintf")
    formatStringFunctions.forEach { func ->
        if (content.contains(func.toByteArray())) {
            vulnerabilities.add("Potential format string vulnerability: Use of function $func without proper format specifiers")
        }
    }

    // Check for integer overflow vulnerabilities
    if (content.contains("malloc".toByteArray()) && !content.contains("size_t".toByteArray())) {
        vulnerabilities.add("Potential integer overflow vulnerability: Use of malloc without proper size checks")
    }

    // Check for use-after-free vulnerabilities
    if (content.contains("free".toByteArray()) && content.contains("memcpy".toByteArray())) {
        vulnerabilities.add("Potential use-after-free vulnerability: Memory access after free")
    }

    // Check for null pointer dereference
    if (content.contains("->"u8.toByteArray()) && !content.contains("null".toByteArray())) {
        vulnerabilities.add("Potential null pointer dereference: Use of -> operator without null checks")
    }

    return vulnerabilities
}