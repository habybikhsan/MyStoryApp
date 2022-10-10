package com.example.myStoryApp.data.repository

import com.example.myStoryApp.api.ApiService

class StoryRepository(private val apiService: ApiService) {
    fun getStory(token: String) = apiService.getStories(token)
}