package com.example.mystoryapp.data.repository

import com.example.mystoryapp.api.ApiService

class StoryRepository(private val apiService: ApiService) {
    fun getStory(token: String) = apiService.getStories(token)
}