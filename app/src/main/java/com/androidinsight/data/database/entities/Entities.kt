package com.androidinsight.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "apk_info")
data class ApkInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
    val minSdkVersion: Int,
    val targetSdkVersion: Int,
    val analyzedAt: Date,
    val filePath: String
)

@Entity(tableName = "analysis_results")
data class AnalysisResult(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val apkInfoId: Long,
    val permissions: List<String>,
    val activities: Map<String, String>, // activity name -> exported status
    val services: Map<String, String>, // service name -> exported status
    val receivers: Map<String, String>, // receiver name -> exported status
    val providers: Map<String, String>, // provider name -> authority
    val vulnerabilities: List<String>,
    val securityScore: Int,
    val analysisDate: Date,
    val decompilationStatus: String
)

@Entity(tableName = "code_analysis")
data class CodeAnalysis(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val apkInfoId: Long,
    val className: String,
    val methodName: String,
    val permissionUsage: List<String>,
    val intentOperations: List<String>,
    val dataFlowIssues: List<String>,
    val securityIssues: List<String>
)

@Entity(tableName = "manifest_components")
data class ManifestComponent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val apkInfoId: Long,
    val type: String, // activity, service, receiver, provider
    val name: String,
    val exported: Boolean,
    val permission: String?,
    val intentFilters: List<String>
)