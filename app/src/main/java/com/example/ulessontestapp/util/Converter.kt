package com.example.ulessontestapp.util

import androidx.room.TypeConverter
import com.example.ulessontestapp.data.model.Chapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromChapterList(chapterList: List<Chapter>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Chapter>>() {}.type
        return gson.toJson(chapterList, type)
    }

    @TypeConverter
    fun toChapterList(chapterStringList: String): List<Chapter> {
        val gson = Gson()
        val type = object : TypeToken<List<Chapter>>() {}.type
        return gson.fromJson(chapterStringList, type)
    }
}