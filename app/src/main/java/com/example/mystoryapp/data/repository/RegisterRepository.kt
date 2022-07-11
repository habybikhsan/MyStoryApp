package com.example.mystoryapp.data.repository

import com.example.mystoryapp.api.ApiService
import com.example.mystoryapp.data.RequestRegister

class RegisterRepository (private val apiService: ApiService) {
    fun createUser(requestRegister: RequestRegister) = apiService.createUser(requestRegister)
}