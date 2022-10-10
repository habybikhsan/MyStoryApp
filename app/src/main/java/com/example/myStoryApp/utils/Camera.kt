package com.example.myStoryApp.utils

import android.content.Context
import android.os.Environment
import com.example.myStoryApp.ui.uploadStory.UploadStoryActivity.Companion.FILENAME_FORMAT
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

// Untuk kasus Intent Camera
fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

