package com.example.ulessontestapp.data.model

import androidx.room.PrimaryKey

data class Chapter(
    @PrimaryKey
    val id: Long,
    val lessons: List<Lesson>,
    val name: String
)