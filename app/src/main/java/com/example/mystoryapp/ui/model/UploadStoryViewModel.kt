package com.example.mystoryapp.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.repository.UploadStoryRepository
import com.example.mystoryapp.data.response.ResponseMsg
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadStoryViewModel(private val repository: UploadStoryRepository) : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun upload(photo: MultipartBody.Part, des: RequestBody, token: String) {
        _isLoading.value = true
        val service = repository.uploadImage(photo, des, "Bearer $token")
        service.enqueue(object : Callback<ResponseMsg> {
            override fun onResponse(
                call: Call<ResponseMsg>,
                response: Response<ResponseMsg>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _message.value = responseBody.message
                    }
                } else {
                    _message.value = response.message()

                }
            }

            override fun onFailure(call: Call<ResponseMsg>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Failed Retrofit Instance"
            }
        })
    }
}