package com.example.ulessontestapp.data.sources.repository

import androidx.lifecycle.LiveData
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.util.Resource

interface Repository {

    fun fetchSubjects(): LiveData<Resource<Unit>>

    fun getSubjects(): LiveData<List<Subject>>

    suspend fun saveRecentWatch(recentlyWatched: RecentlyWatched)

    fun getRecentWatch(limit: Int): LiveData<List<RecentlyWatched>>

    suspend fun saveSubjects(subjects: List<Subject>)

    suspend fun getSubject(id: Long): Resource<Subject>
}