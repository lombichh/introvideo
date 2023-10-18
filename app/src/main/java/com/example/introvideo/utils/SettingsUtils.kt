package com.example.introvideo.utils

import android.content.Context
import android.content.SharedPreferences
import java.math.BigInteger
import java.security.MessageDigest

class SettingsUtils {

    companion object {
        // shared preferences ids
        val settingsSharedPrefsName: String = "settings"

        val video1PathId: String = "video1_path"
        val video2PathId: String = "video2_path"
        val video3PathId: String = "video3_path"
        val video4PathId: String = "video4_path"

        val cover1PathId: String = "video1_cover_path"
        val cover2PathId: String = "video2_cover_path"
        val cover3PathId: String = "video3_cover_path"
        val cover4PathId: String = "video4_cover_path"

        val video1VisibilityId: String = "video1_visibility"
        val video2VisibilityId: String = "video2_visibility"
        val video3VisibilityId: String = "video3_visibility"
        val video4VisibilityId: String = "video4_visibility"

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
            messageDigest.update(password.toByteArray(Charsets.UTF_8))

            val hashedPassword = messageDigest.digest()
            val hashedPasswordString = BigInteger(1, hashedPassword).toString()

            return hashedPasswordString
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