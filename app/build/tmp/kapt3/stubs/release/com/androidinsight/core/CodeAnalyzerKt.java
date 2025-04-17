package com.androidinsight.core;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010$\n\u0002\b\u0003\u001a\u0006\u0010\u0000\u001a\u00020\u0001\u001a\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0015\u0010\t\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a2\u0006\u0002\u0010\n\u001a\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\r\u001a\u00020\u0006\u001a-\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002\u00a2\u0006\u0002\u0010\u0016\u001a\u0015\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0004H\u0002\u00a2\u0006\u0002\u0010\u0018\u001a\u0010\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u001b\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u001e\u001a\u0010\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0015\u0010 \u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a2\u0006\u0002\u0010\n\u001a\u0015\u0010 \u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a2\u0006\u0002\u0010\n\u001a\u0010\u0010!\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001f\u0010\"\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u001bH\u0002\u00a2\u0006\u0002\u0010$\u001a\u0015\u0010%\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a2\u0006\u0002\u0010\n\u001a\u0010\u0010&\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\'\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010(\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0015\u0010)\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a2\u0006\u0002\u0010\n\u001a\u0015\u0010)\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u00a2\u0006\u0002\u0010\n\u001a\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0018\u0010+\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010#\u001a\u00020\u001bH\u0002\u001a\u0010\u0010,\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010-\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u001e\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010/\u001a\u00020\u0012H\u0002\u001a\u001c\u00100\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b012\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0015\u00102\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u00103\u00a8\u00064"}, d2 = {"<no name provided>", "", "analyzeCryptoOperations", "", "error/NonExistentClass", "libFile", "Ljava/io/File;", "content", "", "analyzeLibrarySecurity", "([B)Lerror/NonExistentClass;", "analyzeMemoryCorruption", "analyzeNativeLibraries", "apkFile", "checkFullRELRO", "", "elfHeader", "sectionHeaderOffset", "", "sectionHeaderEntrySize", "", "sectionHeaderCount", "(Lerror/NonExistentClass;JII)Z", "checkNXBit", "(Lerror/NonExistentClass;)Z", "checkStackCanaries", "decompileMethod", "", "dexFile", "methodSignature", "(Ljava/io/File;Lerror/NonExistentClass;)Ljava/lang/String;", "detectAntiDebugTechniques", "detectBufferOverflow", "detectEmulator", "detectKeySize", "algorithm", "([BLjava/lang/String;)Ljava/lang/Integer;", "detectMemoryCorruption", "detectObfuscation", "detectRootDetection", "detectTamperProtection", "detectUseAfterFree", "detectVulnerabilities", "isCustomCryptoImplementation", "isLibraryObfuscated", "isStripped", "parseExportTable", "offset", "parseSymbolTable", "", "performSecurityAnalysis", "(Ljava/io/File;)Lerror/NonExistentClass;", "app_release"})
public final class CodeAnalyzerKt {
    
    private static final java.util.List<java.lang.String> detectVulnerabilities(java.io.File libFile) {
        return null;
    }
    
    private static final error.NonExistentClass performSecurityAnalysis(java.io.File libFile) {
        return null;
    }
    
    private static final boolean detectAntiDebugTechniques(java.io.File libFile) {
        return false;
    }
    
    private static final boolean detectRootDetection(java.io.File libFile) {
        return false;
    }
    
    private static final boolean detectObfuscation(java.io.File libFile) {
        return false;
    }
    
    private static final boolean detectEmulator(java.io.File libFile) {
        return false;
    }
    
    private static final boolean detectTamperProtection(java.io.File libFile) {
        return false;
    }
    
    private static final java.util.List<error.NonExistentClass> analyzeCryptoOperations(java.io.File libFile) {
        return null;
    }
    
    private static final boolean isLibraryObfuscated(java.io.File libFile) {
        return false;
    }
    
    private static final boolean isStripped(java.io.File libFile) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.util.List<error.NonExistentClass> analyzeNativeLibraries(@org.jetbrains.annotations.NotNull()
    java.io.File apkFile) {
        return null;
    }
    
    private static final java.util.Map<java.lang.String, java.lang.String> parseSymbolTable(byte[] content) {
        return null;
    }
    
    private static final boolean checkFullRELRO(error.NonExistentClass elfHeader, long sectionHeaderOffset, int sectionHeaderEntrySize, int sectionHeaderCount) {
        return false;
    }
    
    private static final boolean checkNXBit(error.NonExistentClass elfHeader) {
        return false;
    }
    
    private static final boolean checkStackCanaries(byte[] content) {
        return false;
    }
    
    private static final java.util.List<java.lang.String> parseExportTable(byte[] content, long offset) {
        return null;
    }
    
    private static final java.util.List<error.NonExistentClass> analyzeCryptoOperations(byte[] content) {
        return null;
    }
    
    private static final java.lang.Integer detectKeySize(byte[] content, java.lang.String algorithm) {
        return null;
    }
    
    private static final boolean isCustomCryptoImplementation(byte[] content, java.lang.String algorithm) {
        return false;
    }
    
    private static final java.util.List<error.NonExistentClass> analyzeMemoryCorruption(byte[] content) {
        return null;
    }
    
    private static final error.NonExistentClass detectMemoryCorruption(byte[] content) {
        return null;
    }
    
    private static final error.NonExistentClass detectBufferOverflow(byte[] content) {
        return null;
    }
    
    private static final error.NonExistentClass detectUseAfterFree(byte[] content) {
        return null;
    }
    
    private static final error.NonExistentClass detectBufferOverflow(byte[] content) {
        return null;
    }
    
    private static final error.NonExistentClass detectUseAfterFree(byte[] content) {
        return null;
    }
    
    private static final error.NonExistentClass analyzeLibrarySecurity(byte[] content) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String decompileMethod(@org.jetbrains.annotations.NotNull()
    java.io.File dexFile, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass methodSignature) {
        return null;
    }
    
    private static final java.util.List<java.lang.String> detectVulnerabilities(byte[] content) {
        return null;
    }
}