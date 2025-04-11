package com.androidinsight.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androidinsight.data.database.dao.AnalysisResultDao
import com.androidinsight.data.database.dao.ApkInfoDao
import com.androidinsight.data.database.entities.AnalysisResult
import com.androidinsight.data.database.entities.ApkInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(
    entities = [
        ApkInfo::class,
        AnalysisResult::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun apkInfoDao(): ApkInfoDao
    abstract fun analysisResultDao(): AnalysisResultDao
}

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromMap(value: Map<String, String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType)
    }
}