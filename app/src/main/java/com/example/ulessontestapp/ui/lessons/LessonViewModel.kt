package com.example.ulessontestapp.ui.lessons

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.sources.repository.Repository
import kotlinx.coroutines.launch

class LessonViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    fun addRecentWatched(recentlyWatched: RecentlyWatched) {
        viewModelScope.launch {
            repository.saveRecentWatch(recentlyWatched)
        }
    }
}