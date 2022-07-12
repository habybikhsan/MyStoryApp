package com.example.mystoryapp.data.repository

import com.example.mystoryapp.api.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadStoryRepository (private val apiService: ApiService) {
    fun uploadImage(photo : MultipartBody.Part,
    description : RequestBody,
    token : String) = apiService.uploadImage(photo, description, token)
}