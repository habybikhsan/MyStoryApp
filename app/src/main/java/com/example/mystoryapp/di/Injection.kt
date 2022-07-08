package com.example.mystoryapp.di

import com.example.mystoryapp.BuildConfig
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.data.repository.LoginRepository
import com.example.mystoryapp.data.repository.StoryRepository

object Injection {

    fun provideStoryRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService(BuildConfig.BASEURL)
        return StoryRepository(apiService)
    }

    fun provideLoginRepository() : LoginRepository{
        val apiService = ApiConfig.getApiService(BuildConfig.BASEURL)
        return LoginRepository(apiService)
    }
}