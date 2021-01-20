package com.example.ulessontestapp.data.sources.local

import androidx.lifecycle.LiveData
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.util.Resource

interface SubjectLocalDataSource {
    suspend fun saveSubjects(subjects: List<Subject>)

    suspend fun saveRecentWatch(recentlyWatched: RecentlyWatched)

    fun observeSubjects(): LiveData<List<Subject>>

    suspend fun getSubject(id: Long): Resource<Subject>

    fun observeRecentWatch(limit: Int): LiveData<List<RecentlyWatched>>

}