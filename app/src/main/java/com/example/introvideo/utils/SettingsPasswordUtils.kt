package com.example.introvideo.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.security.MessageDigest

class SettingsPasswordUtils {

    companion object {
        val sharedPreferencesName: String = "IntroVideoSharedPreferences"
        val settingsPasswordId: String = "settings_password"

        fun hashPassword(password: String): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedPassword = messageDigest.digest(password.toByteArray())
            return Base64.encodeToString(hashedPassword, Base64.DEFAULT)
        }

        fun savePassword(context: Context, newPassword: String) {
            val sharedPreferences =
                context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            val hashedPassword = hashPassword(newPassword)

            editor.putString(settingsPasswordId, hashedPassword)
            editor.apply()
        }

        fun isCorrectPassword(context: Context, enteredPassword: String): Boolean {
            val enteredHashedPassword = hashPassword(enteredPassword)

            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
            val storedHashedPassword = sharedPreferences.getString(settingsPasswordId, "")

            return enteredHashedPassword == storedHashedPassword
        }
    }

}