package com.example.myStoryApp.data.repository

import com.example.myStoryApp.api.ApiService
import com.example.myStoryApp.data.RequestRegister

class RegisterRepository (private val apiService: ApiService) {
    fun createUser(requestRegister: RequestRegister) = apiService.createUser(requestRegister)
}