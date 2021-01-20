package com.example.ulessontestapp.data.sources.remote

import com.example.ulessontestapp.data.model.SubjectResponse
import retrofit2.Response
import retrofit2.http.GET

interface SubjectService {
    @GET("content/grade")
    suspend fun getSubjectData(): Response<SubjectResponse>
}