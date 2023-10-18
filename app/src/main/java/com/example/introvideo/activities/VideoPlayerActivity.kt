package com.example.introvideo.activities

import android.content.Context
import android.media.AudioManager
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
    private var audioLevel: Int = 75

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
            audioLevel = extras.getInt("audio_level")
        }
    }

    private fun initViews() {
        videoView = findViewById(R.id.videoview)
    }

    private fun initVideo() {
        videoView.setVideoPath(videoPath)

        // set device audio level
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioLevel, 0)

        videoView.start()

        // get back to main activity when video ends
        videoView.setOnCompletionListener { finish() }
    }

}