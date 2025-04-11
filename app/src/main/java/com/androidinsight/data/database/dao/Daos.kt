package com.androidinsight.data.database.dao

import androidx.room.*
import com.androidinsight.data.database.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ApkInfoDao {
    @Insert
    suspend fun insert(apkInfo: ApkInfo): Long

    @Update
    suspend fun update(apkInfo: ApkInfo)

    @Delete
    suspend fun delete(apkInfo: ApkInfo)

    @Query("SELECT * FROM apk_info WHERE id = :id")
    suspend fun getById(id: Long): ApkInfo?

    @Query("SELECT * FROM apk_info ORDER BY analyzedAt DESC")
    fun getAllApks(): Flow<List<ApkInfo>>

    @Query("SELECT * FROM apk_info WHERE packageName = :packageName")
    suspend fun getByPackageName(packageName: String): ApkInfo?
}

@Dao
interface AnalysisResultDao {
    @Insert
    suspend fun insert(analysisResult: AnalysisResult): Long

    @Update
    suspend fun update(analysisResult: AnalysisResult)

    @Query("SELECT * FROM analysis_results WHERE apkInfoId = :apkInfoId")
    suspend fun getByApkInfoId(apkInfoId: Long): AnalysisResult?

    @Query("SELECT * FROM analysis_results ORDER BY analysisDate DESC")
    fun getAllResults(): Flow<List<AnalysisResult>>

    @Transaction
    @Query("SELECT * FROM analysis_results WHERE securityScore < 70")
    fun getVulnerableApps(): Flow<List<AnalysisResult>>
}

@Dao
interface CodeAnalysisDao {
    @Insert
    suspend fun insert(codeAnalysis: CodeAnalysis): Long

    @Insert
    suspend fun insertAll(codeAnalyses: List<CodeAnalysis>)

    @Query("SELECT * FROM code_analysis WHERE apkInfoId = :apkInfoId")
    suspend fun getByApkInfoId(apkInfoId: Long): List<CodeAnalysis>

    @Query("SELECT * FROM code_analysis WHERE className LIKE :className")
    suspend fun getByClassName(className: String): List<CodeAnalysis>

    @Query("SELECT * FROM code_analysis WHERE securityIssues != '[]'")
    fun getWithSecurityIssues(): Flow<List<CodeAnalysis>>
}

@Dao
interface ManifestComponentDao {
    @Insert
    suspend fun insert(component: ManifestComponent): Long

    @Insert
    suspend fun insertAll(components: List<ManifestComponent>)

    @Query("SELECT * FROM manifest_components WHERE apkInfoId = :apkInfoId")
    suspend fun getByApkInfoId(apkInfoId: Long): List<ManifestComponent>

    @Query("SELECT * FROM manifest_components WHERE type = :componentType AND exported = 1")
    suspend fun getExportedComponents(componentType: String): List<ManifestComponent>

    @Query("SELECT * FROM manifest_components WHERE permission IS NULL AND exported = 1")
    suspend fun getUnprotectedComponents(): List<ManifestComponent>
}