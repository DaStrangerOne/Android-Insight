package com.androidinsight.data.repository

import android.content.Context
import android.content.pm.PackageManager
import com.androidinsight.data.database.entities.ApkInfo
import com.androidinsight.data.database.entities.AnalysisResult
import com.androidinsight.data.database.entities.ManifestComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.Date
import java.util.zip.ZipFile

class ApkAnalysisRepository {
    suspend fun analyzeApk(context: Context, apkFile: File): ApkInfo = withContext(Dispatchers.IO) {
        try {
            val packageInfo = context.packageManager.getPackageArchiveInfo(
                apkFile.absolutePath,
                PackageManager.GET_ACTIVITIES or
                        PackageManager.GET_SERVICES or
                        PackageManager.GET_RECEIVERS or
                        PackageManager.GET_PROVIDERS or
                        PackageManager.GET_PERMISSIONS
            )

            requireNotNull(packageInfo) { "Unable to parse APK file" }

            ApkInfo(
                packageName = packageInfo.packageName,
                versionName = packageInfo.versionName ?: "",
                versionCode = packageInfo.versionCode,
                minSdkVersion = packageInfo.applicationInfo.minSdkVersion,
                targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion,
                analyzedAt = Date(),
                filePath = apkFile.absolutePath
            )
        } catch (e: Exception) {
            throw RuntimeException("Failed to analyze APK: ${e.message}")
        }
    }

    suspend fun extractManifestComponents(context: Context, apkFile: File): List<ManifestComponent> =
        withContext(Dispatchers.IO) {
            val packageInfo = context.packageManager.getPackageArchiveInfo(
                apkFile.absolutePath,
                PackageManager.GET_ACTIVITIES or
                        PackageManager.GET_SERVICES or
                        PackageManager.GET_RECEIVERS or
                        PackageManager.GET_PROVIDERS
            ) ?: throw RuntimeException("Unable to parse APK file")

            val components = mutableListOf<ManifestComponent>()

            // Extract activities
            packageInfo.activities?.forEach { activityInfo ->
                components.add(
                    ManifestComponent(
                        apkInfoId = 0, // Will be set after ApkInfo is saved
                        type = "activity",
                        name = activityInfo.name,
                        exported = activityInfo.exported,
                        permission = activityInfo.permission,
                        intentFilters = listOf() // TODO: Extract intent filters
                    )
                )
            }

            // Similar extraction for services, receivers, and providers
            // TODO: Implement extraction for other component types

            components
        }

    suspend fun saveApkInfo(apkInfo: ApkInfo) {
        // TODO: Implement Room database operations
    }

    suspend fun saveAnalysisResult(result: AnalysisResult) {
        // TODO: Implement Room database operations
    }

    suspend fun saveManifestComponents(components: List<ManifestComponent>) {
        // TODO: Implement Room database operations
    }
}