package com.example.introvideo.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore

class VideoUtils {

    companion object {
        fun getRealPathFromUri(context: Context, uri: Uri): String? {
            val projection = arrayOf(MediaStore.Video.Media.DATA)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)

            cursor?.use {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                it.moveToFirst()
                return it.getString(columnIndex)
            }

            return null
        }

        fun getPhysicalPath(context: Context, uri: Uri?): String? {
            if (uri == null) {
                return null
            }

            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                    && uri.authority == "com.android.providers.media.documents"
                ) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    val contentUri: Uri = when (type) {
                        "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        else -> return null
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                return getDataColumn(context, uri, null, null)
            }

            return null
        }

        private fun getDataColumn(
            context: Context, uri: Uri, selection: String?,
            selectionArgs: Array<String>?
        ): String? {
            val column = "_data"
            val projection = arrayOf(column)
            context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(column)
                    return it.getString(columnIndex)
                }
            }
            return null
        }
    }

}