package com.androidinsight.data.database

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)

    @TypeConverter
    fun fromList(list: List<String>): String = gson.toJson(list)

    @TypeConverter
    fun toList(json: String): List<String> = gson.fromJson(json, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromMap(map: Map<String, String>): String = gson.toJson(map)

    @TypeConverter
    fun toMap(json: String): Map<String, String> = gson.fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
}