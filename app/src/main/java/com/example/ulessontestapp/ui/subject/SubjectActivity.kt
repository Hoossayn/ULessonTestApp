package com.example.ulessontestapp.ui.subject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ulessontestapp.R
import com.example.ulessontestapp.data.helper.EventObserver
import com.example.ulessontestapp.ui.chapter.ChapterActivity
import com.example.ulessontestapp.ui.lessons.LessonActivity
import com.example.ulessontestapp.ui.subject.adapters.RecentWatchAdapter
import com.example.ulessontestapp.ui.subject.adapters.SubjectAdapter
import com.example.ulessontestapp.util.Resource
import com.example.ulessontestapp.util.Resource.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_subjects.*

@AndroidEntryPoint
class SubjectActivity : AppCompatActivity(), RecentWatchAdapter.CharacterItemListener {

    private val viewModel: SubjectViewModel by viewModels()
    private lateinit var adapter: SubjectAdapter
    private lateinit var recentWatchAdapter: RecentWatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects)

        adapter = SubjectAdapter(viewModel)
        recentWatchAdapter = RecentWatchAdapter(this)

        subjectList.adapter = adapter
        recentWatchList.adapter = recentWatchAdapter

        subjectList.layoutManager = GridLayoutManager(this, 2)
        viewModel.getSubjects()

        setupObservers()
        setUpToggleViewClickListener()
        viewModel.toggleButton(toggleViewText.text.toString())

    }

    private fun setUpToggleViewClickListener() {
        toggleBtn.setOnClickListener {
            viewModel.toggleButton(toggleViewText.text.toString())
        }
    }

    private fun setupObservers() {
        viewModel.fetchingSubject.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    loading.visibility = View.GONE
                }
                Status.ERROR -> {
                    loading.visibility = View.GONE
                    showError("Network Error: check your internet connection")
                }
            }
        })

        viewModel.subjects.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
            }
        })

        viewModel.openSubjectId.observe(this, EventObserver {

            val intent = Intent(this, ChapterActivity::class.java)
            intent.putExtra(ChapterActivity.EXTRAS_CHAPTER_ID, it)
            startActivity(intent)

        })



        viewModel.recentViews.observe(this, Observer {
            if (it.isNotEmpty()) {
                emptyRecent.visibility = View.GONE
                toggleViewContainer.visibility = View.VISIBLE
            }

            recentWatchAdapter.submitList(it)
        })

        viewModel.toggleText.observe(this, Observer {
            toggleViewText.text = it
        })
    }

    private fun showError(msg: String) {
        Snackbar.make(vParent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    override fun onClickedCharacter(
        mediaUrl: String,
        subjectId: Long,
        subjectName: String,
        topicName: String
    ) {
        val intent = Intent(this, LessonActivity::class.java)
        intent.putExtra(LessonActivity.EXTRAS_MEDIA_URL, mediaUrl)
        intent.putExtra(LessonActivity.EXTRAS_SUBJECT_ID, subjectId)
        intent.putExtra(LessonActivity.EXTRAS_SUBJECT_NAME, subjectName)
        intent.putExtra(LessonActivity.EXTRAS_TOPIC_NAME, topicName)
        startActivity(intent)
    }


}