package com.example.introvideo.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.security.MessageDigest

class SettingsUtils {

    companion object {
        val settingsSharedPrefsName: String = "settings"

        val video1PathId: String = "video1_path"
        val video2PathId: String = "video2_path"
        val video3PathId: String = "video3_path"
        val video4PathId: String = "video4_path"

        val video1CoverPathId: String = "video1_cover_path"
        val video2CoverPathId: String = "video2_cover_path"
        val video3CoverPathId: String = "video3_cover_path"
        val video4CoverPathId: String = "video4_cover_path"

        val video1VisibleId: String = "video1_visible"
        val video2VisibleId: String = "video2_visible"
        val video3VisibleId: String = "video3_visible"
        val video4VisibleId: String = "video4_visible"

        val audioLevelId: String = "audio_level"

        val hashedPasswordId: String = "hashed_password"

        fun isPasswordStored(context: Context): Boolean {
            val sharedPreferences =
                context.getSharedPreferences(settingsSharedPrefsName, Context.MODE_PRIVATE)
            val storedHashedPassword = sharedPreferences.getString(hashedPasswordId, "")

            return storedHashedPassword != ""
        }

        private fun hashPassword(password: String): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val hashedPassword = messageDigest.digest(password.toByteArray())
            return Base64.encodeToString(hashedPassword, Base64.DEFAULT)
        }

        fun savePassword(context: Context, newPassword: String) {
            val sharedPreferences =
                context.getSharedPreferences(settingsSharedPrefsName, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            if (newPassword != "") {
                // update password in shared preferences
                val hashedPassword = hashPassword(newPassword)
                editor.putString(hashedPasswordId, hashedPassword)
            } else {
                // remove password from shared preferences
                editor.remove(hashedPasswordId)
            }

            editor.apply()
        }

        fun isPasswordCorrect(context: Context, enteredPassword: String): Boolean {
            val enteredHashedPassword = hashPassword(enteredPassword)

            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(settingsSharedPrefsName, Context.MODE_PRIVATE)
            val storedHashedPassword = sharedPreferences.getString(hashedPasswordId, "")

            return enteredHashedPassword == storedHashedPassword
        }
    }

}