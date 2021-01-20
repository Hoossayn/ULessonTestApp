package com.example.ulessontestapp.ui.subject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ulesson.util.MainCoroutineRule
import com.example.ulesson.util.getOrAwaitValue
import com.example.ulessontestapp.data.FakeRepository
import com.example.ulessontestapp.data.sources.repository.DefaultRepository
import com.example.ulessontestapp.util.Resource
import com.example.ulessontestapp.util.TestObjectUtil
import kotlinx.coroutines.test.runBlockingTest
import net.bytebuddy.pool.TypePool
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
class SubjectViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutine = MainCoroutineRule()
    private val repository = FakeRepository()

    private lateinit var viewModel: SubjectViewModel


    @Before
    fun setup() {
        viewModel = SubjectViewModel(repository)
    }

    @Test
    fun `assert that call to network passes`() {
        mainCoroutine.runBlockingTest {
            viewModel.getSubjects()

            val status = viewModel.fetchingSubject.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.success(Unit)))
        }
    }

    @Test
    fun `assert that error response is received when error occurs calling network`() {
        mainCoroutine.runBlockingTest {
            repository.setShouldReturnError(true)
            viewModel.getSubjects()

            val status = viewModel.fetchingSubject.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.error("error occurred")))
        }
    }

    @Test
    fun `assert that call to network saves subject data`() {
        mainCoroutine.runBlockingTest {
            viewModel.getSubjects()

            val status = viewModel.fetchingSubject.getOrAwaitValue()
            MatcherAssert.assertThat(status, `is`(Resource.success(Unit)))

            val subjects = viewModel.subjects.getOrAwaitValue()
            MatcherAssert.assertThat(subjects, `is`(TestObjectUtil.subjects))
        }
    }

    @Test
    fun `assert that clicking view all button changes the button text to view less`() {
        //initial state of button
        viewModel.toggleButton("VIEW ALL")

        val textBtn = viewModel.toggleText.getOrAwaitValue()
        MatcherAssert.assertThat(textBtn, `is`("VIEW LESS"))
    }

    @Test
    fun `assert that clicking view less button changes the button text to view all`() {
        viewModel.toggleButton("VIEW LESS")

        val textBtn = viewModel.toggleText.getOrAwaitValue()
        MatcherAssert.assertThat(textBtn, `is`("VIEW ALL"))
    }

    @Test
    fun `assert that clicking view less button receives two(2) list of recent watched topics`() {
        repository.saveAllRecentView(TestObjectUtil.recentViews)

        viewModel.toggleButton("VIEW LESS")

        val recentViews = viewModel.recentViews.getOrAwaitValue()
        MatcherAssert.assertThat(recentViews.size, `is`(2))
        MatcherAssert.assertThat(recentViews, `is`(TestObjectUtil.recentViews.take(2)))
    }

    @Test
    fun `assert that clicking view all button receives all the list of recent watched topics`() {
        repository.saveAllRecentView(TestObjectUtil.recentViews)

        viewModel.toggleButton("VIEW ALL")

        val recentViews = viewModel.recentViews.getOrAwaitValue()
        MatcherAssert.assertThat(recentViews.size, `is`(TestObjectUtil.recentViews.size))
        MatcherAssert.assertThat(recentViews, `is`(TestObjectUtil.recentViews))
    }

    @Test
    fun `assert that open subject receives sets livedata`() {
        val subjectIdToOpen = TestObjectUtil.subjects[0].id
        viewModel.openSubject(subjectIdToOpen)

        val subjectId = viewModel.openSubjectId.getOrAwaitValue().getContentIfNotHandled()
        MatcherAssert.assertThat(subjectId, `is`(notNullValue()))
        MatcherAssert.assertThat(subjectId, `is`(subjectIdToOpen))
    }

}