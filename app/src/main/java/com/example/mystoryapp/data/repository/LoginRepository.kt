package com.example.mystoryapp.data.repository

import com.example.mystoryapp.api.ApiService
import com.example.mystoryapp.data.RequestLogin

class LoginRepository (private val apiService: ApiService) {
    fun fetchUser (requestLogin: RequestLogin) = apiService.fetchUser(requestLogin)
}