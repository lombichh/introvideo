package com.example.introvideo.utils

import java.security.KeyStore

class KeyStoreUtils {

    companion object {
        const val alias = "intro_video"

        fun existsPassword(): Boolean {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            return keyStore.containsAlias(alias)
        }
    }

}