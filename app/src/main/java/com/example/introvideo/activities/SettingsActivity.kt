package com.example.introvideo.activities

import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Toast
import com.example.introvideo.R
import com.example.introvideo.utils.SettingsPasswordUtils

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initUi()
    }

    private fun initUi() {
        val saveButton: Button = findViewById(R.id.save_button)
        val passwordEditText: EditText = findViewById(R.id.password_edittext)

        saveButton.setOnClickListener{
            SettingsPasswordUtils.savePassword(this, passwordEditText.text.toString())
        }

        val customSeekBar: SeekBar = findViewById(R.id.audio_seekbar)

        val customSeekBarDrawable = customSeekBar.progressDrawable as LayerDrawable
        customSeekBarDrawable.setLayerHeight(0, 15) // background height
        customSeekBarDrawable.setLayerHeight(1, 15) // progress height
    }
}