package com.example.myStoryApp.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun AppCompatActivity.loadImageViaGlide(uri: Uri?, target: ImageView) {
    Glide
        .with(this)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(target)
}


fun Context.loadImageViaGlide(uri: Uri?, target: ImageView) {
    Glide
        .with(this)
        .load(uri)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(target)
}

fun AppCompatActivity.loadImageViaGlide(drawable: Int, target: ImageView) {
    Glide
        .with(this)
        .load(ContextCompat.getDrawable(this, drawable))
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(target)
}