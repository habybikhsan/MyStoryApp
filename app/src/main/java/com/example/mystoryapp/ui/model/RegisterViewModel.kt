package com.example.mystoryapp.ui.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mystoryapp.data.RequestRegister
import com.example.mystoryapp.data.repository.RegisterRepository
import com.example.mystoryapp.data.response.ResponseMsg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    var isError: Boolean = false

    fun getResponseRegister(requestRegister: RequestRegister) {
        _isLoading.value = true
        val call = repository.createUser(requestRegister)
        call.enqueue(object : Callback<ResponseMsg> {
            override fun onResponse (cal : Call<ResponseMsg>, response: Response<ResponseMsg>){
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    isError = false
                    _message.value = responseBody?.message.toString()
                }else{
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseMsg>, t: Throwable) {
                isError = true
                _isLoading.value = false
            }
        })
    }
}