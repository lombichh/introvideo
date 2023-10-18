package com.example.introvideo.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class FileUtils {

    companion object {

        private val FILE_CACHE_DIR = "file_cache"

        @Throws(IOException::class)
        fun copyFileInCache(context: Context, uri: Uri?): String? {
            if (uri != null) {
                val fileInputStream = context.contentResolver.openInputStream(uri)

                if (fileInputStream != null) {
                    val fileCacheDir = File(context.getExternalFilesDir(null), FILE_CACHE_DIR)
                    var isFileCacheDirExists = fileCacheDir.exists()
                    if (!isFileCacheDirExists) {
                        isFileCacheDirExists = fileCacheDir.mkdirs()
                    }

                    if (isFileCacheDirExists) {
                        val filePath =
                            fileCacheDir.absolutePath + "/" + getFileDisplayName(context, uri)
                        val selectedFileOutPutStream: OutputStream = FileOutputStream(filePath)
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (fileInputStream.read(buffer).also { length = it } > 0) {
                            selectedFileOutPutStream.write(buffer, 0, length)
                        }
                        selectedFileOutPutStream.flush()
                        selectedFileOutPutStream.close()
                        return filePath
                    }

                    fileInputStream.close()
                }
            }

            return null
        }

        private fun getFileDisplayName(context: Context, uri: Uri): String? {
            var displayName: String? = null
            context.contentResolver
                .query(uri, null, null, null, null, null).use { cursor ->
                    if (cursor != null && cursor.moveToFirst()) {
                        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (index >= 0) displayName = cursor.getString(index)
                    }
                }
            return displayName
        }
    }

}