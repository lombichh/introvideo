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
        val passwordEditText: EditText = findViewById(R.id.update_password_edittext)
        val saveButton: Button = findViewById(R.id.save_button)
        val getPasswordButton: Button = findViewById(R.id.get_password_button)

        saveButton.setOnClickListener{
            SettingsPasswordUtils.savePassword(this, passwordEditText.text.toString())
        }

        getPasswordButton.setOnClickListener {
            if (SettingsPasswordUtils.isCorrectPassword(this, passwordEditText.text.toString())) {
                Toast.makeText(this, "Correct Password", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
        }
    }
}