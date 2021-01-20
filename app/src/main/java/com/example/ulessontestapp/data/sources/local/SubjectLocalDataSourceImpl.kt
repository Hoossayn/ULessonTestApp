package com.example.ulessontestapp.data.sources.local

import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.data.sources.local.dao.RecentlyWatchedDao
import com.example.ulessontestapp.data.sources.local.dao.SubjectDao
import com.example.ulessontestapp.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubjectLocalDataSourceImpl @Inject constructor(
    private val subjectDao: SubjectDao,
    private val recentlyWatchedDao: RecentlyWatchedDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SubjectLocalDataSource {

    override suspend fun saveSubjects(subjects: List<Subject>) = withContext(ioDispatcher) {
        subjectDao.saveSubjects(subjects)
    }

    override suspend fun saveRecentWatch(recentlyWatched: RecentlyWatched) = withContext(ioDispatcher) {
        recentlyWatchedDao.saveRecentWatch(recentlyWatched)
    }

    override fun observeSubjects() = subjectDao.getAllSubjects()

    override fun observeRecentWatch(limit: Int) = recentlyWatchedDao.getRecentWatch(limit)

    override suspend fun getSubject(id: Long) = withContext(ioDispatcher) {
        val subject = subjectDao.getSubject(id)
        return@withContext Resource.success(subject)
    }


}