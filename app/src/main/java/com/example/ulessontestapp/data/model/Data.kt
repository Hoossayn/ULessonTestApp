package com.example.ulessontestapp.data.model

import com.example.ulessontestapp.data.model.entities.Subject


data class Data(
    val status:String,
    val message:String,
    val subjects: List<Subject>
)