package com.example.ulessontestapp.data.model

import com.google.gson.annotations.SerializedName

data class Lesson(
    val icon: String,
    val id: Int,
    val name: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("subject_id")
    val subjectId: Long,
    @SerializedName("chapter_id")
    val chapterId: Long

    )