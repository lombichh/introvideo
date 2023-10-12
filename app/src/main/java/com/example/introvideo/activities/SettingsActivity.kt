package com.example.introvideo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    }
}