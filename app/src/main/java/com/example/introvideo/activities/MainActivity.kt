package com.example.introvideo.activities

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.introvideo.R
import com.example.introvideo.utils.SettingsUtils
import com.google.android.material.imageview.ShapeableImageView
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var settingsSharedPrefs: SharedPreferences

    // views
    private lateinit var settingsImageView: ImageView

    private lateinit var firstTableRow: TableRow
    private lateinit var secondTableRow: TableRow

    private lateinit var video1Layout: ConstraintLayout
    private lateinit var video2Layout: ConstraintLayout
    private lateinit var video3Layout: ConstraintLayout
    private lateinit var video4Layout: ConstraintLayout

    private lateinit var video1ImageView: ShapeableImageView
    private lateinit var video2ImageView: ShapeableImageView
    private lateinit var video3ImageView: ShapeableImageView
    private lateinit var video4ImageView: ShapeableImageView

    // video settings
    private var video1Path: String = ""
    private var video2Path: String = ""
    private var video3Path: String = ""
    private var video4Path: String = ""

    private var cover1Path: String = ""
    private var cover2Path: String = ""
    private var cover3Path: String = ""
    private var cover4Path: String = ""

    private var video1Visibility: Boolean = true
    private var video2Visibility: Boolean = true
    private var video3Visibility: Boolean = true
    private var video4Visibility: Boolean = true

    private var audioLevel: Int = 75

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        loadSharedPreferences()
        initUi()
    }

    private fun loadSharedPreferences() {
        settingsSharedPrefs =
            getSharedPreferences(SettingsUtils.settingsSharedPrefsName, Context.MODE_PRIVATE)

        video1Path = settingsSharedPrefs.getString(SettingsUtils.video1PathId, "").toString()
        video2Path = settingsSharedPrefs.getString(SettingsUtils.video2PathId, "").toString()
        video3Path = settingsSharedPrefs.getString(SettingsUtils.video3PathId, "").toString()
        video4Path = settingsSharedPrefs.getString(SettingsUtils.video4PathId, "").toString()

        cover1Path = settingsSharedPrefs.getString(SettingsUtils.cover1PathId, "").toString()
        cover2Path = settingsSharedPrefs.getString(SettingsUtils.cover2PathId, "").toString()
        cover3Path = settingsSharedPrefs.getString(SettingsUtils.cover3PathId, "").toString()
        cover4Path = settingsSharedPrefs.getString(SettingsUtils.cover4PathId, "").toString()

        video1Visibility =
            settingsSharedPrefs.getBoolean(SettingsUtils.video1VisibilityId, true)
        video2Visibility =
            settingsSharedPrefs.getBoolean(SettingsUtils.video2VisibilityId, true)
        video3Visibility =
            settingsSharedPrefs.getBoolean(SettingsUtils.video3VisibilityId, true)
        video4Visibility =
            settingsSharedPrefs.getBoolean(SettingsUtils.video4VisibilityId, true)

        audioLevel = settingsSharedPrefs.getInt(SettingsUtils.audioLevelId, 75)
    }

    private fun initUi() {
        initViewVars()
        initViewValues()
    }

    private fun initViewVars() {
        settingsImageView = findViewById(R.id.settings_imageview)

        firstTableRow = findViewById(R.id.first_tablerow)
        secondTableRow = findViewById(R.id.second_tablerow)

        video1Layout = findViewById(R.id.video1_constraintlayout)
        video2Layout = findViewById(R.id.video2_constraintlayout)
        video3Layout = findViewById(R.id.video3_constraintlayout)
        video4Layout = findViewById(R.id.video4_constraintlayout)

        video1ImageView = findViewById(R.id.video1_imageview)
        video2ImageView = findViewById(R.id.video2_imageview)
        video3ImageView = findViewById(R.id.video3_imageview)
        video4ImageView = findViewById(R.id.video4_imageview)
    }

    private fun initViewValues() {
        settingsImageView.setOnClickListener {
            openSettings()
        }

        // show videos and covers only if they exists
        val video1File = File(video1Path)
        if (video1Visibility && video1File.exists()) {
            video1Layout.setOnClickListener {
                startVideoActivity(video1Path)
            }

            val cover1File = File(cover1Path)
            if (cover1File.exists()) {
                val cover1Bitmap = BitmapFactory.decodeFile(cover1File.absolutePath)
                video1ImageView.setImageBitmap(cover1Bitmap)
            } else video1ImageView.setBackgroundColor(getColor(R.color.gray))

            video1Layout.visibility = ConstraintLayout.VISIBLE
        } else video1Layout.visibility = ConstraintLayout.GONE

        val video2File = File(video2Path)
        if (video2Visibility && video2File.exists()) {
            video2Layout.setOnClickListener {
                startVideoActivity(video2Path)
            }

            val cover2File = File(cover2Path)
            if (cover2File.exists()) {
                val cover2Bitmap = BitmapFactory.decodeFile(cover2File.absolutePath)
                video2ImageView.setImageBitmap(cover2Bitmap)
            } else video2ImageView.setBackgroundColor(getColor(R.color.gray))

            video2Layout.visibility = ConstraintLayout.VISIBLE
        } else video2Layout.visibility = ConstraintLayout.GONE

        val video3File = File(video3Path)
        if (video3Visibility && video3File.exists()) {
            video3Layout.setOnClickListener {
                startVideoActivity(video3Path)
            }

            val cover3File = File(cover3Path)
            if (cover3File.exists()) {
                val cover3Bitmap = BitmapFactory.decodeFile(cover3File.absolutePath)
                video3ImageView.setImageBitmap(cover3Bitmap)
            } else video3ImageView.setBackgroundColor(getColor(R.color.gray))

            video3Layout.visibility = ConstraintLayout.VISIBLE
        } else video3Layout.visibility = ConstraintLayout.GONE

        val video4File = File(video4Path)
        if (video4Visibility && video4File.exists()) {
            video4Layout.setOnClickListener {
                startVideoActivity(video4Path)
            }

            val cover4File = File(cover4Path)
            if (cover4File.exists()) {
                val cover4Bitmap = BitmapFactory.decodeFile(cover4File.absolutePath)
                video4ImageView.setImageBitmap(cover4Bitmap)
            } else video4ImageView.setBackgroundColor(getColor(R.color.gray))

            video4Layout.visibility = ConstraintLayout.VISIBLE
        } else video4Layout.visibility = ConstraintLayout.GONE

        // collapse TableRow if his child have visibility = GONE
        if (video1Layout.visibility == ConstraintLayout.GONE
            && video2Layout.visibility == ConstraintLayout.GONE
        ) {
            firstTableRow.visibility = TableRow.GONE
        } else firstTableRow.visibility = TableRow.VISIBLE
        if (video3Layout.visibility == ConstraintLayout.GONE
            && video4Layout.visibility == ConstraintLayout.GONE
        ) {
            secondTableRow.visibility = TableRow.GONE
        } else secondTableRow.visibility = TableRow.VISIBLE
    }

    private fun startVideoActivity(videoPath: String) {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        intent.putExtra("video_path", videoPath)
        intent.putExtra("audio_level", audioLevel)
        startActivity(intent)
    }

    private fun openSettings() {
        // if password exists then open password settings activity before entering the settings
        if (SettingsUtils.isPasswordStored(this))
            startActivity(Intent(this, PasswordSettingsActivity::class.java))
        else startActivity(Intent(this, SettingsActivity::class.java))
    }
}