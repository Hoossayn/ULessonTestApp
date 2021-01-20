package com.example.ulessontestapp.ui.chapter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessontestapp.R
import com.example.ulessontestapp.data.model.Chapter
import com.example.ulessontestapp.ui.chapter.ChapterViewModel
import kotlinx.android.synthetic.main.chapter_item.view.*

class ChapterAdapter(private val viewModel: ChapterViewModel) :
    ListAdapter<Chapter, ChapterAdapter.ViewHolder>(
        ChapterDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.chapter_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(viewModel: ChapterViewModel, chapter: Chapter) {
            with(itemView) {
                chapterName.text = chapter.name
                val adapter =
                    LessonAdapter(
                        viewModel
                    )
                lessonsList.adapter = adapter
                adapter.submitList(chapter.lessons)
            }
        }
    }
}


class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {
    override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem == newItem
    }
}