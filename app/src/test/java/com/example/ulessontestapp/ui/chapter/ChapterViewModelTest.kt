package com.example.ulessontestapp.ui.chapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ulesson.util.MainCoroutineRule
import com.example.ulessontestapp.data.FakeRepository
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
        viewModel = ChapterViewModel(repository)

    }




}