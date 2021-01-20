package com.example.ulessontestapp.data.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ulessontestapp.data.model.Chapter

@Entity(tableName = "subjects")
data class Subject(
    val chapters: List<Chapter>,
    val icon: String,
    @PrimaryKey
    val id: Long,
    val name: String
)