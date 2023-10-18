package com.example.introvideo.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Typeface
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.documentfile.provider.DocumentFile
import com.example.introvideo.R
import com.example.introvideo.utils.FileUtils
import com.example.introvideo.utils.SettingsUtils
import com.example.introvideo.utils.VideoUtils
import java.util.*


class SettingsActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView

    private lateinit var settingsSharedPrefs: SharedPreferences

    // views
    lateinit var backImageView: ImageView
    lateinit var saveButton: Button

    lateinit var video1PathTextView: TextView
    lateinit var video2PathTextView: TextView
    lateinit var video3PathTextView: TextView
    lateinit var video4PathTextView: TextView

    lateinit var selectVideo1FrameLayout: FrameLayout
    lateinit var selectVideo2FrameLayout: FrameLayout
    lateinit var selectVideo3FrameLayout: FrameLayout
    lateinit var selectVideo4FrameLayout: FrameLayout

    lateinit var cover1PathTextView: TextView
    lateinit var cover2PathTextView: TextView
    lateinit var cover3PathTextView: TextView
    lateinit var cover4PathTextView: TextView

    lateinit var selectCover1FrameLayout: FrameLayout
    lateinit var selectCover2FrameLayout: FrameLayout
    lateinit var selectCover3FrameLayout: FrameLayout
    lateinit var selectCover4FrameLayout: FrameLayout

    lateinit var video1VisibilityImageView: ImageView
    lateinit var video2VisibilityImageView: ImageView
    lateinit var video3VisibilityImageView: ImageView
    lateinit var video4VisibilityImageView: ImageView

    lateinit var audioLevelSeekBar: SeekBar

    lateinit var passwordEditText: EditText
    lateinit var passwordVisibilityImageView: ImageView

    // settings vars
    private var isSettingsSaved: Boolean = true

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

    private var passwordText: String = ""
    private var passwordVisibility: Boolean = false

    // activity result launchers
    private lateinit var selectVideo1Launcher: ActivityResultLauncher<Intent>
    private lateinit var selectVideo2Launcher: ActivityResultLauncher<Intent>
    private lateinit var selectVideo3Launcher: ActivityResultLauncher<Intent>
    private lateinit var selectVideo4Launcher: ActivityResultLauncher<Intent>

    private lateinit var selectCover1Launcher: ActivityResultLauncher<Intent>
    private lateinit var selectCover2Launcher: ActivityResultLauncher<Intent>
    private lateinit var selectCover3Launcher: ActivityResultLauncher<Intent>
    private lateinit var selectCover4Launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

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

        video1Visibility = settingsSharedPrefs.getBoolean(SettingsUtils.video1VisibilityId, true)
        video2Visibility = settingsSharedPrefs.getBoolean(SettingsUtils.video2VisibilityId, true)
        video3Visibility = settingsSharedPrefs.getBoolean(SettingsUtils.video3VisibilityId, true)
        video4Visibility = settingsSharedPrefs.getBoolean(SettingsUtils.video4VisibilityId, true)

        audioLevel = settingsSharedPrefs.getInt(SettingsUtils.audioLevelId, 75)
    }

    private fun initUi() {
        initViewVars()
        initViewValues()
        initSelectPathLaunchers()
        initOnClickListeners()
    }

    private fun initViewVars() {
        // Toolbar
        backImageView = findViewById(R.id.back_imageview)
        saveButton = findViewById(R.id.save_button)

        // Videos
        video1PathTextView = findViewById(R.id.video1_path_textview)
        video2PathTextView = findViewById(R.id.video2_path_textview)
        video3PathTextView = findViewById(R.id.video3_path_textview)
        video4PathTextView = findViewById(R.id.video4_path_textview)

        selectVideo1FrameLayout = findViewById(R.id.select_video1_framelayout)
        selectVideo2FrameLayout = findViewById(R.id.select_video2_framelayout)
        selectVideo3FrameLayout = findViewById(R.id.select_video3_framelayout)
        selectVideo4FrameLayout = findViewById(R.id.select_video4_framelayout)

        cover1PathTextView = findViewById(R.id.cover1_path_textview)
        cover2PathTextView = findViewById(R.id.cover2_path_textview)
        cover3PathTextView = findViewById(R.id.cover3_path_textview)
        cover4PathTextView = findViewById(R.id.cover4_path_textview)

        selectCover1FrameLayout = findViewById(R.id.select_cover1_framelayout)
        selectCover2FrameLayout = findViewById(R.id.select_cover2_framelayout)
        selectCover3FrameLayout = findViewById(R.id.select_cover3_framelayout)
        selectCover4FrameLayout = findViewById(R.id.select_cover4_framelayout)

        video1VisibilityImageView = findViewById(R.id.video1_visibility_imageview)
        video2VisibilityImageView = findViewById(R.id.video2_visibility_imageview)
        video3VisibilityImageView = findViewById(R.id.video3_visibility_imageview)
        video4VisibilityImageView = findViewById(R.id.video4_visibility_imageview)

        // Audio
        audioLevelSeekBar = findViewById(R.id.audio_seekbar)

        // Password
        passwordEditText = findViewById(R.id.password_edittext)
        passwordVisibilityImageView = findViewById(R.id.password_visibility_imageview)
    }

    private fun initViewValues() {
        // Videos
        video1PathTextView.text = video1Path
        video2PathTextView.text = video2Path
        video3PathTextView.text = video3Path
        video4PathTextView.text = video4Path

        cover1PathTextView.text = cover1Path
        cover2PathTextView.text = cover2Path
        cover3PathTextView.text = cover3Path
        cover4PathTextView.text = cover4Path

        if (video1Visibility) {
            video1VisibilityImageView.setImageResource(R.drawable.visible)
            video1VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
        } else {
            video1VisibilityImageView.setImageResource(R.drawable.hidden)
            video1VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
        }
        if (video2Visibility) {
            video2VisibilityImageView.setImageResource(R.drawable.visible)
            video2VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
        } else {
            video2VisibilityImageView.setImageResource(R.drawable.hidden)
            video2VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
        }
        if (video3Visibility) {
            video3VisibilityImageView.setImageResource(R.drawable.visible)
            video3VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
        } else {
            video3VisibilityImageView.setImageResource(R.drawable.hidden)
            video3VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
        }
        if (video4Visibility) {
            video4VisibilityImageView.setImageResource(R.drawable.visible)
            video4VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
        } else {
            video4VisibilityImageView.setImageResource(R.drawable.hidden)
            video4VisibilityImageView.colorFilter =
                PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
        }

        // Audio
        val customSeekBarDrawable = audioLevelSeekBar.progressDrawable as LayerDrawable
        customSeekBarDrawable.setLayerHeight(0, 15) // background height
        customSeekBarDrawable.setLayerHeight(1, 15) // progress height
    }

    private fun initSelectPathLaunchers() {
        // videos
        selectVideo1Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result != null && result.data != null) {
                    // add new video in cache
                    video1Path = FileUtils.copyFileInCache(this, result.data!!.data).toString()

                    // update ui
                    video1PathTextView.text = video1Path
                    updateSettingsSaved()
                }
            }
        }
        selectVideo2Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result != null && result.data != null) {
                    // add new video in cache
                    video2Path = FileUtils.copyFileInCache(this, result.data!!.data).toString()

                    // update ui
                    video2PathTextView.text = video2Path
                    updateSettingsSaved()
                }
            }
        }
        selectVideo3Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result != null && result.data != null) {
                    // add new video in cache
                    video3Path = FileUtils.copyFileInCache(this, result.data!!.data).toString()

                    // update ui
                    video3PathTextView.text = video3Path
                    updateSettingsSaved()
                }
            }
        }
        selectVideo4Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result != null && result.data != null) {
                    // add new video in cache
                    video4Path = FileUtils.copyFileInCache(this, result.data!!.data).toString()

                    // update ui
                    video4PathTextView.text = video4Path
                    updateSettingsSaved()
                }
            }
        }

        // covers
        selectCover1Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // update settings vars and ui
                cover1Path = VideoUtils.getPhysicalPath(this, result.data?.data).toString()
                cover1PathTextView.text = cover1Path
                updateSettingsSaved()
            }
        }
        selectCover2Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // update settings vars and ui
                cover2Path = VideoUtils.getPhysicalPath(this, result.data?.data).toString()
                cover2PathTextView.text = cover2Path
                updateSettingsSaved()
            }
        }
        selectCover3Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // update settings vars and ui
                cover3Path = VideoUtils.getPhysicalPath(this, result.data?.data).toString()
                cover3PathTextView.text = cover3Path
                updateSettingsSaved()
            }
        }
        selectCover4Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // update settings vars and ui
                cover4Path = VideoUtils.getPhysicalPath(this, result.data?.data).toString()
                cover4PathTextView.text = cover4Path
                updateSettingsSaved()
            }
        }
    }

    private fun isVideoFile(fileName: String): Boolean {
        val videoExtensions = arrayOf("mp4", "avi", "mkv", "mov", "wmv", "flv")
        val extension = fileName.substringAfterLast(".")
        return extension.lowercase(Locale.ROOT) in videoExtensions
    }

    private fun initOnClickListeners() {
        // Toolbar
        backImageView.setOnClickListener{
            finish()
        }
        saveButton.setOnClickListener{
            if (!isSettingsSaved) {
                saveSettings()

                saveButton.background =
                    ContextCompat.getDrawable(this, R.drawable.rounded_rect_stroke_10dp)
                saveButton.text = getString(R.string.saved)
                saveButton.setTextColor(getColor(R.color.green))

                isSettingsSaved = true
            }
        }

        // Videos
        selectVideo1FrameLayout.setOnClickListener{
            selectVideo1Launcher.launch(getSelectFileLauncherIntent("video/*"))
        }
        selectVideo2FrameLayout.setOnClickListener{
            selectVideo2Launcher.launch(getSelectFileLauncherIntent("video/*"))
        }
        selectVideo3FrameLayout.setOnClickListener{
            selectVideo3Launcher.launch(getSelectFileLauncherIntent("video/*"))
        }
        selectVideo4FrameLayout.setOnClickListener{
            selectVideo4Launcher.launch(getSelectFileLauncherIntent("video/*"))
        }

        selectCover1FrameLayout.setOnClickListener{
            selectCover1Launcher.launch(getSelectFileLauncherIntent("image/*"))
        }
        selectCover2FrameLayout.setOnClickListener{
            selectCover2Launcher.launch(getSelectFileLauncherIntent("image/*"))
        }
        selectCover3FrameLayout.setOnClickListener{
            selectCover3Launcher.launch(getSelectFileLauncherIntent("image/*"))
        }
        selectCover4FrameLayout.setOnClickListener{
            selectCover4Launcher.launch(getSelectFileLauncherIntent("image/*"))
        }

        video1VisibilityImageView.setOnClickListener{
            Log.d("lombichh", "--- outside result")
            Log.d("lombichh", "folder path: content://com.android.externalstorage.documents/tree/primary%3AMovies")
            val treeUri: Uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AMovies")
            val documentFile = DocumentFile.fromTreeUri(this, treeUri)

            Log.d("lombichh", "exists: ${documentFile?.exists()}")
            if (documentFile != null && documentFile.isDirectory) {
                val files = documentFile.listFiles()
                for (file in files) {
                    if (file.isFile && isVideoFile(file.name!!)) {
                        Log.d("lombichh", "video file: ${file.name}")
                    }
                }
            }

            if (video1Visibility) {
                video1VisibilityImageView.setImageResource(R.drawable.hidden)
                video1VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
                video1Visibility = false
            } else {
                video1VisibilityImageView.setImageResource(R.drawable.visible)
                video1VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                video1Visibility = true
            }

            updateSettingsSaved()
        }
        video2VisibilityImageView.setOnClickListener{
            if (video2Visibility) {
                video2VisibilityImageView.setImageResource(R.drawable.hidden)
                video2VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
                video2Visibility = false
            } else {
                video2VisibilityImageView.setImageResource(R.drawable.visible)
                video2VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                video2Visibility = true
            }

            updateSettingsSaved()
        }
        video3VisibilityImageView.setOnClickListener{
            if (video3Visibility) {
                video3VisibilityImageView.setImageResource(R.drawable.hidden)
                video3VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
                video3Visibility = false
            } else {
                video3VisibilityImageView.setImageResource(R.drawable.visible)
                video3VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                video3Visibility = true
            }

            updateSettingsSaved()
        }
        video4VisibilityImageView.setOnClickListener{
            if (video4Visibility) {
                video4VisibilityImageView.setImageResource(R.drawable.hidden)
                video4VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
                video4Visibility = false
            } else {
                video4VisibilityImageView.setImageResource(R.drawable.visible)
                video4VisibilityImageView.colorFilter =
                    PorterDuffColorFilter(getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                video4Visibility = true
            }

            updateSettingsSaved()
        }

        // Audio
        audioLevelSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
                audioLevel = progress
                updateSettingsSaved()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        // Password
        passwordEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                passwordText = passwordEditText.text.toString()
                updateSettingsSaved()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        passwordVisibilityImageView.setOnClickListener{
            if (passwordVisibility) {
                passwordVisibilityImageView.setImageResource(R.drawable.hidden)
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                // change font to sofia pro regular because type_class_text
                // automatically change it to default font
                val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.sofia_pro_regular)
                passwordEditText.setTypeface(typeface)

                passwordVisibility = false
            } else {
                passwordVisibilityImageView.setImageResource(R.drawable.visible)
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                // change font to sofia pro regular because type_class_text
                // automatically change it to default font
                val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.sofia_pro_regular)
                passwordEditText.setTypeface(typeface)

                passwordVisibility = true
            }
        }
    }

    private fun getSelectFileLauncherIntent(intentType: String): Intent {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = intentType

        return intent
    }

    private fun saveSettings() {
        // save settings to shared preferences
        with (settingsSharedPrefs.edit()) {
            // Videos
            putString(SettingsUtils.video1PathId, video1Path)
            putString(SettingsUtils.video2PathId, video2Path)
            putString(SettingsUtils.video3PathId, video3Path)
            putString(SettingsUtils.video4PathId, video4Path)

            putString(SettingsUtils.cover1PathId, cover1Path)
            putString(SettingsUtils.cover2PathId, cover2Path)
            putString(SettingsUtils.cover3PathId, cover3Path)
            putString(SettingsUtils.cover4PathId, cover4Path)

            putBoolean(SettingsUtils.video1VisibilityId, video1Visibility)
            putBoolean(SettingsUtils.video2VisibilityId, video2Visibility)
            putBoolean(SettingsUtils.video3VisibilityId, video3Visibility)
            putBoolean(SettingsUtils.video4VisibilityId, video4Visibility)

            // Audio
            putInt(SettingsUtils.audioLevelId, audioLevelSeekBar.progress)

            // Password
            if (passwordEditText.text.toString() != "") {
                SettingsUtils
                    .savePassword(this@SettingsActivity, passwordEditText.text.toString())
            }

            apply()
        }
    }

    private fun updateSettingsSaved() {
        if (isSettingsSaved) {
            saveButton.background =
                ContextCompat.getDrawable(this, R.drawable.rounded_rect_10dp)
            saveButton.text = getString(R.string.save)
            saveButton.setTextColor(getColor(R.color.white))

            isSettingsSaved = false
        }
    }
}