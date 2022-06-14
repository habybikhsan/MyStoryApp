package com.example.mystoryapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Story (
    val photoUrl: String? = null,
    val createdAt: String? = null,
    val name: String? = null,
    val description: String? = null,
    val id: String? = null,
    val lon: Double? = null,
    val lat: Double? = null
): Parcelable