package com.example.introvideo.activities

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.introvideo.R


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
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (maxVolume * audioLevel) / 100, 0)

        // keep video aspect ratio
        videoView.setOnPreparedListener { mediaPlayer ->
            val videoWidth = mediaPlayer.videoWidth
            val videoHeight = mediaPlayer.videoHeight
            val videoProportion = videoWidth.toFloat() / videoHeight.toFloat()
            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels
            val screenProportion = screenWidth.toFloat() / screenHeight.toFloat()
            val lp = videoView.layoutParams
            if (videoProportion > screenProportion) {
                lp.height = (screenWidth / videoProportion).toInt()
            } else {
                lp.height = screenHeight
            }
            videoView.layoutParams = lp
        }

        // start video
        videoView.start()

        // get back to main activity when video ends
        videoView.setOnCompletionListener { finish() }
    }

}