package com.example.introvideo.activities

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
        Log.d("lombichh", "video_path: $videoPath")

        var uri = Uri.parse(videoPath)

        if (uri.scheme == "content") {
            val filePath = getRealPathFromUri(uri)
            if (filePath != null) {
                uri = Uri.parse(filePath)
                val file = File(filePath)
                Log.d("lombichh", "file_path: $filePath")
                Log.d("lombichh", "exists: ${file.exists()}")
                playVideo(uri)
            }
        } else {
            playVideo(uri)
        }
    }

    private fun getRealPathFromUri(uri: Uri): String? {
        val documentId = DocumentsContract.getDocumentId(uri)
        val contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Video.Media._ID + " = ?"
        val selectionArgs = arrayOf(documentId)

        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, selection, selectionArgs, null)

        return if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            if (cursor.moveToFirst()) {
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                filePath
            } else {
                null
            }
        } else {
            null
        }
    }

    private fun playVideo(uri: Uri) {
        videoView.setVideoURI(uri)
        videoView.start()
    }

}