package com.example.mystoryapp.utils

import android.app.Application
import android.content.Context
import android.os.Environment
import com.example.mystoryapp.R
import com.example.mystoryapp.ui.activity.UploadStoryActivity.Companion.FILENAME_FORMAT
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

// Untuk kasus CameraX
fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}