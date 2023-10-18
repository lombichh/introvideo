package com.example.introvideo.activities

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.documentfile.provider.DocumentFile
import com.example.introvideo.R
import com.example.introvideo.utils.SettingsUtils
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var settingsSharedPrefs: SharedPreferences

    // views
    private lateinit var settingsImageView: ImageView

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

    // activity result launchers
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPermissionsLauncher()
    }

    override fun onResume() {
        super.onResume()

        launchPermissionsRequest()
    }

    private fun initPermissionsLauncher() {
        requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
            { results ->
                loadSharedPreferences()
                initUi()
            }
    }

    private fun launchPermissionsRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionsLauncher.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO))
        } else {
            requestPermissionsLauncher.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
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
    }

    private fun initUi() {
        initViewVars()
        initViewValues()
    }

    private fun initViewVars() {
        settingsImageView = findViewById(R.id.settings_imageview)

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

            }

            val cover1File = getFirstImageInFolder(cover1Path)
            if (cover1File != null) {
                val cover1Bitmap = BitmapFactory.decodeFile(cover1File.absolutePath)
                video1ImageView.setImageBitmap(cover1Bitmap)
            }

            video1Layout.visibility = ConstraintLayout.VISIBLE
        } else video1Layout.visibility = ConstraintLayout.GONE

        val video2File = File(video2Path)
        if (video2Visibility && video2File.exists()) {
            video2Layout.setOnClickListener {

            }

            val cover2File = getFirstImageInFolder(cover2Path)
            if (cover2File != null) {
                val cover2Bitmap = BitmapFactory.decodeFile(cover2File.absolutePath)
                video2ImageView.setImageBitmap(cover2Bitmap)
            }

            video2Layout.visibility = ConstraintLayout.VISIBLE
        } else video2Layout.visibility = ConstraintLayout.GONE

        val video3File = File(video3Path)
        if (video3Visibility && video3File.exists()) {
            video3Layout.setOnClickListener {

            }

            val cover3File = getFirstImageInFolder(cover3Path)
            if (cover3File != null) {
                val cover3Bitmap = BitmapFactory.decodeFile(cover3File.absolutePath)
                video3ImageView.setImageBitmap(cover3Bitmap)
            }

            video3Layout.visibility = ConstraintLayout.VISIBLE
        } else video3Layout.visibility = ConstraintLayout.GONE

        val video4File = File(video4Path)
        if (video4Visibility && video4File.exists()) {
            video4Layout.setOnClickListener {

            }

            val cover4File = getFirstImageInFolder(cover4Path)
            if (cover4File != null) {
                val cover4Bitmap = BitmapFactory.decodeFile(cover4File.absolutePath)
                video4ImageView.setImageBitmap(cover4Bitmap)
            }

            video4Layout.visibility = ConstraintLayout.VISIBLE
        } else video4Layout.visibility = ConstraintLayout.GONE
    }

    private fun getFirstVideoInFolder(folderPath: String): DocumentFile? {
        Log.d("lombichh", "folder path: $folderPath")
        if (folderPath != "") {
            val treeUri: Uri =
                Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AMovies")
            val documentFile = DocumentFile.fromTreeUri(this, treeUri)

            Log.d("lombichh", "exists: ${documentFile?.exists()}")
            if (documentFile != null && documentFile.isDirectory) {
                val files = documentFile.listFiles()
                for (file in files) {
                    if (file.isFile && isVideoFile(file.name!!)) {
                        return file
                    }
                }
            }
        }

        return null
    }

    private fun isVideoFile(fileName: String): Boolean {
        val videoExtensions = arrayOf("mp4", "avi", "mkv", "mov", "wmv", "flv")
        val extension = fileName.substringAfterLast(".")
        return extension.lowercase(Locale.ROOT) in videoExtensions
    }

    private fun getFirstImageInFolder(folderPath: String): File? {
        val folder = File(folderPath)

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()

            if (files != null) {
                for (file in files) {
                    if (file.isFile) {
                        val fileName = file.name.lowercase(Locale.ROOT)
                        if (fileName.endsWith(".jpg")
                            || fileName.endsWith(".jpeg")
                            || fileName.endsWith(".png")
                            || fileName.endsWith(".gif")
                        ) {
                            return file
                        }
                    }
                }
            }
        }

        return null
    }

    private fun openSettings() {
        // if password exists then open password settings activity before entering the settings
        if (SettingsUtils.isPasswordStored(this))
            startActivity(Intent(this, PasswordSettingsActivity::class.java))
        else startActivity(Intent(this, SettingsActivity::class.java))
    }
}