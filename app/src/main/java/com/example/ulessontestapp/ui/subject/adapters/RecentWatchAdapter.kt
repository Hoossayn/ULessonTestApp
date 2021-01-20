package com.example.ulessontestapp.ui.subject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ulessontestapp.R
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import kotlinx.android.synthetic.main.recent_view_item.view.*

class RecentWatchAdapter(private val listener: CharacterItemListener) : ListAdapter<RecentlyWatched, RecentWatchAdapter.ViewHolder>(RecentWatchDiffCallback()) {


    interface CharacterItemListener {
        fun onClickedCharacter(mediaUrl: String, subjectId:Long, subjectName: String, topicName:String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.recent_view_item, parent, false)
        ,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View, private val listener: RecentWatchAdapter.CharacterItemListener) : RecyclerView.ViewHolder(itemView) {

        private lateinit var recentWatch: RecentlyWatched


        init {
            itemView.setOnClickListener(View.OnClickListener {
                listener.onClickedCharacter(recentWatch.mediaUrl, recentWatch.subjectId, recentWatch.subjectName, recentWatch.topicName)
            })
        }

        fun bind(recentView: RecentlyWatched) {

            this.recentWatch = recentView
            this.recentWatch.mediaUrl = recentView.mediaUrl
            this.recentWatch.subjectId = recentView.subjectId
            this.recentWatch.subjectName = recentView.subjectName
            this.recentWatch.topicName = recentView.topicName

            itemView.subjectTitle.text = recentView.subjectName
            itemView.topicName.text = recentView.topicName

            when (recentView.subjectName) {
                "Biology" -> setContent(
                    itemView,
                    R.color.colorGreen,
                    R.drawable.biology,
                    R.drawable.ic_biology_play
                )
                "Mathematics" -> setContent(
                    itemView,
                    R.color.colorRed,
                    R.drawable.mathematics,
                    R.drawable.ic_play
                )
                "Physics" -> setContent(
                    itemView,
                    R.color.colorPurple,
                    R.drawable.physics,
                    R.drawable.ic_physics_play
                )
                "English" -> setContent(
                    itemView,
                    R.color.colorPurpleDark,
                    R.drawable.biology,
                    R.drawable.ic_english_play
                )
                else -> setContent(
                    itemView,
                    R.color.colorOrange,
                    R.drawable.chemistry,
                    R.drawable.ic_chemistry_play
                )
            }
        }

        private fun setContent(
            itemView: View,
            @ColorRes
            color: Int,
            @DrawableRes
            imageBgDrawable: Int,
            @DrawableRes
            playIconDrawable: Int
        ) {
            itemView.imgContainer.backgroundTintList =
                ContextCompat.getColorStateList(itemView.context, color)

            itemView.imagebg.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    imageBgDrawable
                )
            )
            itemView.playIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    itemView.context,
                    playIconDrawable
                )
            )

            itemView.subjectTitle.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    color
                )
            )
        }
    }
}

class RecentWatchDiffCallback : DiffUtil.ItemCallback<RecentlyWatched>() {
    override fun areItemsTheSame(oldItem: RecentlyWatched, newItem: RecentlyWatched): Boolean {
        return oldItem.subjectId == newItem.subjectId
    }

    override fun areContentsTheSame(oldItem: RecentlyWatched, newItem: RecentlyWatched): Boolean {
        return oldItem == newItem
    }
}