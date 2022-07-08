package com.example.mystoryapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin (
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: List<LoginResult>
){
    data class LoginResult(
        val userId: String,
        val name: String,
        val token: String
    )
}