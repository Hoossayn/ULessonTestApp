package com.example.ulessontestapp.ui.lessons

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.ulessontestapp.R
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_lesson.*
@AndroidEntryPoint
class LessonActivity : AppCompatActivity(), Player.EventListener {

    private val lessonViewModel: LessonViewModel by viewModels()
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var mediaUrl: String? = null
    private var subjectId: Long? = null
    private var subjectName: String? = null
    private var topicName: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)

        intent?.getStringExtra(EXTRAS_MEDIA_URL).let {
            mediaUrl = it
        }
        intent?.getLongExtra(EXTRAS_SUBJECT_ID,0).let {
            subjectId = it
        }
        intent?.getStringExtra(EXTRAS_SUBJECT_NAME).let {
            subjectName = it
        }
        intent?.getStringExtra(EXTRAS_TOPIC_NAME).let {
            topicName = it
        }

        back.setOnClickListener {
            finish()
        }

    }

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this)
        player?.playbackParameters = PlaybackParameters(1.2f)
        player!!.addListener(this)
        playerView.player = player

        val mediaSource = buildMediaSource(Uri.parse(mediaUrl))
        player!!.playWhenReady = playWhenReady
        player!!.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-ulesson")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {

            ExoPlayer.STATE_READY -> {
                lessonViewModel.addRecentWatched(
                    RecentlyWatched(
                        subjectId!!,
                        subjectName!!,
                        topicName!!,
                        mediaUrl!!
                    )
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            player!!.removeListener(this)
            player!!.release()
            player = null
        }
    }

    companion object {
        const val EXTRAS_MEDIA_URL = "media_url"
        const val EXTRAS_SUBJECT_ID = "subject_id"
        const val EXTRAS_SUBJECT_NAME = "subject_name"
        const val EXTRAS_TOPIC_NAME = "topic_name"
    }

}