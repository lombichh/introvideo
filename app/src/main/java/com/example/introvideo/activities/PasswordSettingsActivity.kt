package com.example.introvideo.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.introvideo.R
import com.example.introvideo.utils.SettingsPasswordUtils
import java.security.KeyStore


class PasswordSettingsActivity : AppCompatActivity() {

    // keystore
    var keyStore: KeyStore? = null

    // ui
    var isPasswordVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_settings)

        initUi()
    }

    private fun initUi() {
        val backImageView: ImageView = findViewById(R.id.back_imageview)
        val passwordEditText: EditText = findViewById(R.id.password_edittext)
        val visibilityImageView: ImageView = findViewById(R.id.visibility_imageview)
        val enterFrameLayout: FrameLayout = findViewById(R.id.enter_framelayout)

        backImageView.setOnClickListener{
            finish()
        }

        visibilityImageView.setOnClickListener{
            // change password visibility
            if (isPasswordVisible) {
                visibilityImageView.setImageResource(R.drawable.hidden)
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                // change font to sofia pro regular to because type_class_text
                // automatically change it to default font
                val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.sofia_pro_regular)
                passwordEditText.setTypeface(typeface)

                isPasswordVisible = false
            } else {
                visibilityImageView.setImageResource(R.drawable.visible)
                passwordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                // change font to sofia pro regular to because type_class_text
                // automatically change it to default font
                val typeface: Typeface? = ResourcesCompat.getFont(this, R.font.sofia_pro_regular)
                passwordEditText.setTypeface(typeface)

                isPasswordVisible = true
            }
        }

        enterFrameLayout.setOnClickListener{
            // check if password is correct
            // if correct, open settings activity
            // else, show error toast
            val enteredPassword: String = passwordEditText.text.toString()

            if (SettingsPasswordUtils.isPasswordCorrect(this, enteredPassword))
                startActivity(Intent(this, SettingsActivity::class.java))
            else Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
        }

        passwordEditText.requestFocus()
    }
}