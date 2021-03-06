package com.example.mystoryapp.di

import com.example.mystoryapp.BuildConfig
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.data.repository.LoginRepository
import com.example.mystoryapp.data.repository.RegisterRepository
import com.example.mystoryapp.data.repository.StoryRepository
import com.example.mystoryapp.data.repository.UploadStoryRepository

object Injection {

    fun provideStoryRepository(): StoryRepository {
        val apiService = ApiConfig.getApiService(BuildConfig.BASEURL)
        return StoryRepository(apiService)
    }

    fun provideLoginRepository() : LoginRepository{
        val apiService = ApiConfig.getApiService(BuildConfig.BASEURL)
        return LoginRepository(apiService)
    }

    fun provideRegisterRepository() : RegisterRepository{
        val apiService = ApiConfig.getApiService(BuildConfig.BASEURL)
        return RegisterRepository(apiService)
    }

    fun provideUploadStoryRepository() : UploadStoryRepository{
        val apiService = ApiConfig.getApiService(BuildConfig.BASEURL)
        return UploadStoryRepository(apiService)
    }
}