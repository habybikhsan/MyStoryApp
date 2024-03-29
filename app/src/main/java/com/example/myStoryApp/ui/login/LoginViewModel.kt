package com.example.myStoryApp.ui.login

import androidx.lifecycle.*
import com.example.myStoryApp.data.RequestLogin
import com.example.myStoryApp.data.repository.LoginRepository
import com.example.myStoryApp.data.response.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val repository: LoginRepository) : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _userLogin = MutableLiveData<ResponseLogin>()
    val userLogin: LiveData<ResponseLogin> = _userLogin
    var isError: Boolean = false

    fun login(
        requestLogin: RequestLogin
    ) {
        _isLoading.value = true
        val call = repository.fetchUser(requestLogin)
        call.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                _isLoading.value = false
                val responseBody = response.body()

                if (response.isSuccessful) {
                    isError = false
                    _userLogin.value = responseBody!!
                    _message.value = "Login as ${_userLogin.value!!.loginResult.name}"
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                isError = true
                _isLoading.value = false
                _message.value=t.message.toString()
            }
        })
    }
}