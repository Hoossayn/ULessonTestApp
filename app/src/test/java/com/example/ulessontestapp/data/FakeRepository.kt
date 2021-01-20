package com.example.ulessontestapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.ulessontestapp.util.TestObjectUtil
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.data.sources.repository.Repository
import com.example.ulessontestapp.util.Resource
import com.example.ulessontestapp.util.Resource.*

class FakeRepository : Repository {

    private var shouldReturnError = false

    private val subjects = mutableListOf<Subject>()
    private val recentviews = mutableListOf<RecentlyWatched>()

    private val observeSubjects = MutableLiveData<List<Subject>>()
    private val observeRecentViews = MutableLiveData<List<RecentlyWatched>>()

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    //network call to fetch subjects from the server
    override fun fetchSubjects(): LiveData<Resource<Unit>> {
        return liveData {
            val response = if (shouldReturnError) {
                Resource.error("error occurred", null)
            } else {
                Resource.success(TestObjectUtil.subjects)
            }

            when (response.status) {
                Status.SUCCESS -> {
                    response.data?.let {
                        saveSubjects(it)
                    }
                    emit(Resource.success(Unit))
                }
                Status.ERROR -> {
                    emit(Resource.error(response.message!!, null))
                }
                else -> {
                }
            }
        }
    }

    override fun getSubjects(): LiveData<List<Subject>> {
        return observeSubjects
    }

    override suspend fun saveRecentWatch(recentView: RecentlyWatched) {
        this.recentviews.add(recentView)
        refreshData()
    }

    override fun getRecentWatch(limit: Int): LiveData<List<RecentlyWatched>> {
        observeRecentViews.value = recentviews.take(limit)
        return observeRecentViews
    }

    override suspend fun saveSubjects(subjects: List<Subject>) {
        this.subjects.addAll(subjects)
        refreshData()
    }

    override suspend fun getSubject(id: Long): Resource<Subject> {
        val subject = subjects.find { it.id == id }
        return Resource.success(subject!!)
    }

    fun saveAllRecentView(list: List<RecentlyWatched>) {
        recentviews.addAll(list)
        refreshData()
    }

    private fun refreshData() {
        observeSubjects.value = subjects
        observeRecentViews.value = recentviews
    }

}