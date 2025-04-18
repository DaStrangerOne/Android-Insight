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


    }
    

}