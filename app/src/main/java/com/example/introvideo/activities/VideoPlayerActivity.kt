package com.example.introvideo.activities

import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.introvideo.R

class VideoPlayerActivity : AppCompatActivity() {

    // views
    private lateinit var videoView: VideoView

    // video vars
    private lateinit var videoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        loadExtras()
        initViews()
        initVideo()
    }

    private fun loadExtras() {
        val extras = intent.extras

        if (extras != null) {
            videoPath = extras.getString("video_path").toString()

        }
    }

    private fun initViews() {
        videoView = findViewById(R.id.videoview)
    }

    private fun initVideo() {
        Log.d("lombichh", "video_path: $videoPath")
        videoView.setVideoPath(videoPath)
    }

}