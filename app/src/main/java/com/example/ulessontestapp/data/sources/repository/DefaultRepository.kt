package com.example.ulessontestapp.data.sources.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.data.sources.local.SubjectLocalDataSource
import com.example.ulessontestapp.data.sources.remote.SubjectRemoteDataSource
import com.example.ulessontestapp.util.Resource
import com.example.ulessontestapp.util.Resource.Status
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultRepository(
    private val remoteDataSource: SubjectRemoteDataSource,
    private val localDataSource: SubjectLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repository {

    override fun fetchSubjects(): LiveData<Resource<Unit>> =
        liveData(ioDispatcher) {
            val response = remoteDataSource.getSubjectData()
            when (response.status) {
                Status.LOADING -> {
                    emit(Resource.loading((Unit)))
                }
                Status.SUCCESS -> {
                    response.data?.let { subjectData ->
                        localDataSource.saveSubjects(subjectData.data.subjects)
                    }
                    emit(Resource.success(Unit))
                }
                Status.ERROR -> {
                    emit(Resource.error(response.message!!, null))
                }
            }
        }

    override fun getSubjects() = localDataSource.observeSubjects()

    override suspend fun saveRecentWatch(recentlyWatched: RecentlyWatched) {
        localDataSource.saveRecentWatch(recentlyWatched)
    }

    override fun getRecentWatch(limit: Int) = localDataSource.observeRecentWatch(limit)

    override suspend fun saveSubjects(subjects: List<Subject>) {
        localDataSource.saveSubjects(subjects)
    }

    override suspend fun getSubject(id: Long): Resource<Subject> {
        return localDataSource.getSubject(id)
    }

}