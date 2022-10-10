package com.example.myStoryApp.di

import com.example.myStoryApp.BuildConfig
import com.example.myStoryApp.api.ApiConfig
import com.example.myStoryApp.data.repository.LoginRepository
import com.example.myStoryApp.data.repository.RegisterRepository
import com.example.myStoryApp.data.repository.StoryRepository
import com.example.myStoryApp.data.repository.UploadStoryRepository

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