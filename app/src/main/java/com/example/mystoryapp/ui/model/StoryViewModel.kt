package com.example.mystoryapp.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.repository.StoryRepository
import com.example.mystoryapp.data.response.ResponseStory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel (private val repository: StoryRepository): ViewModel() {

    var storiess: List<ResponseStory.ListStoryItem> = listOf()
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var isError: Boolean = false

    fun getStory(token: String) {
        _isLoading.value = true
        val call = repository.getStory("Bearer $token")
        call.enqueue(object : Callback<ResponseStory> {
            override fun onResponse(
                call: Call<ResponseStory>,
                response: Response<ResponseStory>
            ){
                _isLoading.value = false
                if (response.isSuccessful) {
                    isError = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        storiess = responseBody.listStory
                    }
                    _message.value = responseBody?.message.toString()

                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseStory>, t: Throwable) {
                _isLoading.value = false
                isError = true
                _message.value = t.message.toString()
            }
        })
    }
}