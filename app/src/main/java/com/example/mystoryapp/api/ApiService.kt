package com.example.mystoryapp.api


import com.example.mystoryapp.data.response.ResponseStory
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<ResponseStory>

}