package com.example.mystoryapp.api


import com.example.mystoryapp.data.RequestLogin
import com.example.mystoryapp.data.RequestRegister
import com.example.mystoryapp.data.response.ResponseLogin
import com.example.mystoryapp.data.response.ResponseMsg
import com.example.mystoryapp.data.response.ResponseStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun createUser(@Body requestRegister: RequestRegister): Call<ResponseMsg>

    @POST("login")
    fun fetchUser(@Body requestLogin: RequestLogin): Call<ResponseLogin>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String
    ): Call<ResponseStory>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") token: String
    ): Call<ResponseMsg>

}