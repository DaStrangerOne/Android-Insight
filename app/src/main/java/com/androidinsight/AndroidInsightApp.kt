package com.androidinsight

import android.app.Application
import androidx.room.Room
import com.androidinsight.data.database.AppDatabase

class AndroidInsightApp : Application() {
    companion object {
        lateinit var instance: AndroidInsightApp
            private set
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        
        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "android_insight_db"
        ).build()
        
        // Initialize other components as needed
        initializeComponents()
    }

    private fun initializeComponents() {
        // Initialize APK processing components
        // Initialize analysis modules
        // Set up background job scheduler if needed
    }
}