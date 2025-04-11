package com.androidinsight.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidinsight.AndroidInsightApp
import com.androidinsight.core.ApkProcessor
import com.androidinsight.core.ProcessResult
import com.androidinsight.data.database.entities.AnalysisResult
import com.androidinsight.data.database.entities.ApkInfo
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel : ViewModel() {
    private val apkProcessor = ApkProcessor(AndroidInsightApp.instance)
    private val database = AndroidInsightApp.database

    private val _processingState = MutableLiveData<ProcessingState>(ProcessingState.Idle)
    val processingState: LiveData<ProcessingState> = _processingState

    private val _currentAnalysis = MutableLiveData<AnalysisResult?>()
    val currentAnalysis: LiveData<AnalysisResult?> = _currentAnalysis

    fun processApk(uri: Uri) {
        viewModelScope.launch {
            try {
                _processingState.value = ProcessingState.Processing

                // Process the APK
                when (val result = apkProcessor.processApk(uri)) {
                    is ProcessResult.Success -> {
                        // Save APK info
                        val apkInfo = ApkInfo(
                            packageName = "", // TODO: Extract from manifest
                            versionName = "",
                            versionCode = 0,
                            minSdkVersion = 0,
                            targetSdkVersion = 0,
                            analyzedAt = Date(),
                            filePath = uri.toString()
                        )
                        val apkId = database.apkInfoDao().insert(apkInfo)

                        // Save analysis result
                        val analysisResult = AnalysisResult(
                            apkInfoId = apkId,
                            permissions = result.manifestInfo.permissions,
                            activities = result.manifestInfo.activities.associate { it.name to it.isExported.toString() },
                            services = result.manifestInfo.services.associate { it.name to it.isExported.toString() },
                            receivers = result.manifestInfo.receivers.associate { it.name to it.isExported.toString() },
                            providers = result.manifestInfo.providers.associate { it.name to it.authority },
                            vulnerabilities = emptyList(), // TODO: Implement vulnerability scanning
                            securityScore = calculateSecurityScore(result),
                            analysisDate = Date(),
                            decompilationStatus = "SUCCESS"
                        )
                        database.analysisResultDao().insert(analysisResult)
                        _currentAnalysis.value = analysisResult

                        _processingState.value = ProcessingState.Success("APK analysis completed successfully")
                    }
                    is ProcessResult.Error -> {
                        _processingState.value = ProcessingState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _processingState.value = ProcessingState.Error("Failed to process APK: ${e.message}")
            }
        }
    }

    private fun calculateSecurityScore(result: ProcessResult.Success): Int {
        var score = 100

        // Deduct points for exported components without permissions
        score -= result.manifestInfo.activities.count { it.isExported } * 5
        score -= result.manifestInfo.services.count { it.isExported } * 5
        score -= result.manifestInfo.receivers.count { it.isExported } * 5
        score -= result.manifestInfo.providers.count { it.isExported } * 10

        // Ensure score stays within 0-100 range
        return score.coerceIn(0, 100)
    }
}