package com.example.ulessontestapp.ui.lessons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ulesson.util.MainCoroutineRule
import com.example.ulesson.util.getOrAwaitValue
import com.example.ulessontestapp.data.FakeRepository
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.util.TestObjectUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class LessonViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val repository = FakeRepository()

    private lateinit var viewModel: LessonViewModel


    @Before
    fun setup() {
        viewModel = LessonViewModel(repository)

    }


    @Test
    fun `assert that add to recent view passes`() = mainCoroutineRule.runBlockingTest {

        val recentView = RecentlyWatched(10L, "Chemistry", "Organic Chemistry","chemistry.mp4")
        viewModel.addRecentWatched(recentView)

        val recentViews = repository.getRecentWatch(1000).getOrAwaitValue()
        MatcherAssert.assertThat(recentViews, `is`(not(TestObjectUtil.recentViews)))
        junit.framework.Assert.assertTrue(recentViews.size == 1)
    }
}