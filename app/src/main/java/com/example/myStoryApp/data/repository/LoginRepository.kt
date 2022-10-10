package com.example.myStoryApp.data.repository

import com.example.myStoryApp.api.ApiService
import com.example.myStoryApp.data.RequestLogin

class LoginRepository (private val apiService: ApiService) {
    fun fetchUser (requestLogin: RequestLogin) = apiService.fetchUser(requestLogin)
}