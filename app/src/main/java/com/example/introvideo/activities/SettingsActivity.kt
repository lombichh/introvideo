package com.example.introvideo.activities

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.introvideo.R
import com.example.introvideo.utils.SettingsUtils

class SettingsActivity : AppCompatActivity() {

    private var isSettingsSaved: Boolean = true

    private var video1Path: String = ""
    private var video2Path: String = ""
    private var video3Path: String = ""
    private var video4Path: String = ""

    private var video1CoverPath: String = ""
    private var video2CoverPath: String = ""
    private var video3CoverPath: String = ""
    private var video4CoverPath: String = ""

    private var video1Visible: Boolean = false
    private var video2Visible: Boolean = false
    private var video3Visible: Boolean = false
    private var video4Visible: Boolean = false

    private var audioLevel: Int = 75

    private var passwordText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadSharedPreferences()
        initUi()
    }

    private fun loadSharedPreferences() {
        val settingsSharedPrefs: SharedPreferences =
            getSharedPreferences(SettingsUtils.settingsSharedPrefsName, Context.MODE_PRIVATE)

        video1Path = settingsSharedPrefs.getString(SettingsUtils.video1PathId, "").toString()
        video1Path = settingsSharedPrefs.getString(SettingsUtils.video1PathId, "").toString()
        video1Path = settingsSharedPrefs.getString(SettingsUtils.video1PathId, "").toString()
        video1Path = settingsSharedPrefs.getString(SettingsUtils.video1PathId, "").toString()

        video1CoverPath = settingsSharedPrefs.getString(SettingsUtils.video1CoverPathId, "").toString()
        video2CoverPath = settingsSharedPrefs.getString(SettingsUtils.video2CoverPathId, "").toString()
        video3CoverPath = settingsSharedPrefs.getString(SettingsUtils.video3CoverPathId, "").toString()
        video4CoverPath = settingsSharedPrefs.getString(SettingsUtils.video4CoverPathId, "").toString()

        video1Visible = settingsSharedPrefs.getBoolean(SettingsUtils.video1VisibleId, false)
        video2Visible = settingsSharedPrefs.getBoolean(SettingsUtils.video2VisibleId, false)
        video3Visible = settingsSharedPrefs.getBoolean(SettingsUtils.video3VisibleId, false)
        video4Visible = settingsSharedPrefs.getBoolean(SettingsUtils.video4VisibleId, false)

        audioLevel = settingsSharedPrefs.getInt(SettingsUtils.audioLevelId, 75)
    }

    private fun initUi() {
        // Videos
        val video1PathTextView: TextView = findViewById(R.id.video1_path_textview)
        val video2PathTextView: TextView = findViewById(R.id.video2_path_textview)
        val video3PathTextView: TextView = findViewById(R.id.video3_path_textview)
        val video4PathTextView: TextView = findViewById(R.id.video4_path_textview)

        val selectVideo1FrameLayout: FrameLayout = findViewById(R.id.select_video1_framelayout)
        val selectVideo2FrameLayout: FrameLayout = findViewById(R.id.select_video2_framelayout)
        val selectVideo3FrameLayout: FrameLayout = findViewById(R.id.select_video3_framelayout)
        val selectVideo4FrameLayout: FrameLayout = findViewById(R.id.select_video4_framelayout)

        val cover1PathTextView: TextView = findViewById(R.id.cover1_path_textview)
        val cover2PathTextView: TextView = findViewById(R.id.cover2_path_textview)
        val cover3PathTextView: TextView = findViewById(R.id.cover3_path_textview)
        val cover4PathTextView: TextView = findViewById(R.id.cover4_path_textview)

        val selectCover1FrameLayout: FrameLayout = findViewById(R.id.select_cover1_framelayout)
        val selectCover2FrameLayout: FrameLayout = findViewById(R.id.select_cover2_framelayout)
        val selectCover3FrameLayout: FrameLayout = findViewById(R.id.select_cover3_framelayout)
        val selectCover4FrameLayout: FrameLayout = findViewById(R.id.select_cover4_framelayout)

        val video1VisibilityImageView: ImageView = findViewById(R.id.video1_visibility_imageview)
        val video2VisibilityImageView: ImageView = findViewById(R.id.video2_visibility_imageview)
        val video3VisibilityImageView: ImageView = findViewById(R.id.video3_visibility_imageview)
        val video4VisibilityImageView: ImageView = findViewById(R.id.video4_visibility_imageview)

        video1PathTextView.text = video1Path
        video2PathTextView.text = video2Path
        video3PathTextView.text = video3Path
        video4PathTextView.text = video4Path

        cover1PathTextView.text = video1CoverPath
        cover2PathTextView.text = video2CoverPath
        cover3PathTextView.text = video3CoverPath
        cover4PathTextView.text = video4CoverPath

        video1VisibilityImageView.setImageResource(
            if (video1Visible) R.drawable.visible
            else R.drawable.hidden
        )
        video2VisibilityImageView.setImageResource(
            if (video2Visible) R.drawable.visible
            else R.drawable.hidden
        )
        video3VisibilityImageView.setImageResource(
            if (video3Visible) R.drawable.visible
            else R.drawable.hidden
        )
        video4VisibilityImageView.setImageResource(
            if (video4Visible) R.drawable.visible
            else R.drawable.hidden
        )

        val audioSeekBar: SeekBar = findViewById(R.id.audio_seekbar)
        val passwordEditText: EditText = findViewById(R.id.password_edittext)

        val saveButton: Button = findViewById(R.id.save_button)

        saveButton.setOnClickListener{
            if (!isSettingsSaved) {
                // update shared preferences
                //SettingsPasswordUtils.savePassword(this, passwordEditText.text.toString())

                // update ui
                saveButton.background =
                    AppCompatResources.getDrawable(this, R.drawable.rounded_rect_stroke_10dp)
                saveButton.text = getString(R.string.saved)

                isSettingsSaved = true
            }
        }

        val customSeekBarDrawable = audioSeekBar.progressDrawable as LayerDrawable
        customSeekBarDrawable.setLayerHeight(0, 15) // background height
        customSeekBarDrawable.setLayerHeight(1, 15) // progress height
    }
}