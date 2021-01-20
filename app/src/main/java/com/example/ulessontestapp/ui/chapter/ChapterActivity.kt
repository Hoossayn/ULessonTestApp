package com.example.ulessontestapp.ui.chapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.ulessontestapp.R
import com.example.ulessontestapp.data.helper.EventObserver
import com.example.ulessontestapp.ui.chapter.adapters.ChapterAdapter
import com.example.ulessontestapp.ui.lessons.LessonActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_chapter.*

@AndroidEntryPoint
class ChapterActivity : AppCompatActivity() {

    private val chapterViewModel: ChapterViewModel by viewModels()
    private lateinit var adapter: ChapterAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)

        adapter = ChapterAdapter(chapterViewModel)
        chapterList.adapter = adapter

        intent?.getLongExtra(EXTRAS_CHAPTER_ID, 0)?.let { id ->
            setUpObservers(id)
        } ?: showError("Unknown Error")

        back.setOnClickListener {
            finish()
        }
    }

    private fun setUpObservers(id: Long) {
        chapterViewModel.getSubject(id).observe(this, Observer {
            loading.visibility = View.GONE
            it.data?.let { subject ->
                chapterViewModel.setSubject(subject)
                subjectTitle.text = subject.name
                adapter.submitList(subject.chapters)
            }
        })

        chapterViewModel.navigateToVideo.observe(this, EventObserver {

            val intent = Intent(this, LessonActivity::class.java)
            intent.putExtra(LessonActivity.EXTRAS_MEDIA_URL, it.mediaUrl)
            intent.putExtra(LessonActivity.EXTRAS_SUBJECT_ID, it.subjectId)
            intent.putExtra(LessonActivity.EXTRAS_SUBJECT_NAME, it.subjectName)
            intent.putExtra(LessonActivity.EXTRAS_TOPIC_NAME, it.topicName)
            startActivity(intent)
        })
    }

    companion object {
        const val EXTRAS_CHAPTER_ID = "chapter_id"
    }

    private fun showError(msg: String) {
        Snackbar.make(vDetails, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }
}