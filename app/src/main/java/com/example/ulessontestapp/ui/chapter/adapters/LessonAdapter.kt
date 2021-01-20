package com.example.ulessontestapp.ui.chapter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessontestapp.R
import com.example.ulessontestapp.data.model.Lesson
import com.example.ulessontestapp.ui.chapter.ChapterViewModel
import com.example.ulessontestapp.util.loadImage
import kotlinx.android.synthetic.main.lesson_item.view.*

class LessonAdapter(private val viewModel: ChapterViewModel) :
    ListAdapter<Lesson, LessonAdapter.ViewHolder>(
        LessonDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.lesson_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(viewModel: ChapterViewModel, lesson: Lesson) {
            with(itemView) {
                img.loadImage(lesson.icon)
                lessonTitle.text = lesson.name

                setOnClickListener {
                    viewModel.openVideo(lesson)
                }
            }
        }
    }
}


class LessonDiffCallback : DiffUtil.ItemCallback<Lesson>() {
    override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
        return oldItem == newItem
    }
}