package com.example.ulessontestapp.ui.chapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ulesson.util.MainCoroutineRule
import com.example.ulesson.util.getOrAwaitValue
import com.example.ulessontestapp.data.FakeRepository
import com.example.ulessontestapp.data.helper.Event
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.util.Resource
import com.example.ulessontestapp.util.TestObjectUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ChapterViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    private val repository = FakeRepository()


    private lateinit var viewModel: ChapterViewModel

    @Before
    fun setup() {
        runBlocking {
            repository.saveSubjects(TestObjectUtil.subjects)
        }

        viewModel = ChapterViewModel(repository)

    }


    @Test
    fun `assert that call to get subject receives subject data`() =
        mainCoroutine.runBlockingTest {
            val subjectIdToOpen = TestObjectUtil.subjects[0].id
            val subject = viewModel.getSubject(subjectIdToOpen).getOrAwaitValue()
            MatcherAssert.assertThat(subject.status, `is`(Resource.Status.SUCCESS))
            MatcherAssert.assertThat(subject.data, `is`(TestObjectUtil.subjects[0]))
        }

    @Test
    fun `assert that call to open a video sets navigateToVideo live data when subject is set`() {
        val subject = TestObjectUtil.subjects[0]
        val lesson = TestObjectUtil.lessons[0]

        viewModel.setSubject(subject)
        viewModel.openVideo(lesson)

        val topicName = subject.chapters.find { it.id == lesson.chapterId }!!.name

        val videoDataToOpen = viewModel.navigateToVideo.getOrAwaitValue().getContentIfNotHandled()
        MatcherAssert.assertThat(
            videoDataToOpen,
            `is`(RecentlyWatched(subject.id, subject.name, topicName, lesson.mediaUrl))
        )
    }

    @Test
    fun `assert that call to open a video doesn't set navigateToVideo live data without setting subject`() {

        val observer = com.example.ulesson.util.mock<Observer<Event<RecentlyWatched>>>()

        val lesson = TestObjectUtil.lessons[0]
        viewModel.openVideo(lesson)

        verifyNoMoreInteractions(observer)

    }


}