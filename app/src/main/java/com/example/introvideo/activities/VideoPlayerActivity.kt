package com.example.introvideo.activities

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.example.introvideo.R
import java.io.File
import java.io.FileInputStream




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
        videoView.setVideoPath(videoPath)

        /*val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)*/

        // get back to main activity when video ends
        videoView.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer) {
                finish()
            }
        })

        videoView.start()
    }

}