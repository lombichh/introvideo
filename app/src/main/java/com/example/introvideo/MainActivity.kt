package com.example.introvideo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

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
        startActivity(Intent(this, PasswordSettingsActivity::class.java))
    }
}