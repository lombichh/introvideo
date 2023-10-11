package com.example.introvideo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.introvideo.R

class MainActivity : AppCompatActivity() {

    private lateinit var settingsImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
    }

    private fun initUi() {
        settingsImageView = findViewById(R.id.settings_imageview)

        settingsImageView.setOnClickListener{
            openSettings()
        }
    }

    private fun openSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}