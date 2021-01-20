package com.example.ulessontestapp.ui.chapter

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.ulessontestapp.data.helper.Event
import com.example.ulessontestapp.data.model.Lesson
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.data.sources.repository.Repository
import com.example.ulessontestapp.util.Resource

class ChapterViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    private var subject: Subject? = null

    private val _navigateToVideo = MutableLiveData<Event<RecentlyWatched>>()
    val navigateToVideo: LiveData<Event<RecentlyWatched>> = _navigateToVideo

    fun getSubject(id: Long): LiveData<Resource<Subject>> =
        liveData {
            emit(repository.getSubject(id))
        }

    fun setSubject(subject: Subject) {
        this.subject = subject
    }

    fun openVideo(lesson: Lesson) {
        subject?.let { subject ->
            val topicName = subject.chapters.find { it.id == lesson.chapterId }!!.name
            _navigateToVideo.value = Event(
                RecentlyWatched(subject.id, subject.name, topicName, lesson.mediaUrl)
            )
        }
    }
}