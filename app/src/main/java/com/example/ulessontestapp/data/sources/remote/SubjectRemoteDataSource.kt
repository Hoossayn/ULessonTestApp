package com.example.ulessontestapp.data.sources.remote

import com.example.ulessontestapp.data.model.SubjectResponse
import com.example.ulessontestapp.util.Resource

interface SubjectRemoteDataSource {
    suspend fun getSubjectData(): Resource<SubjectResponse>
}