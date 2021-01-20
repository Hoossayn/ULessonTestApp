package com.example.ulessontestapp.data.model.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "recentlyWatched")
data class RecentlyWatched @JvmOverloads constructor(
    @PrimaryKey
    var subjectId: Long,
    var subjectName: String,
    var topicName: String,
    var mediaUrl: String
)
