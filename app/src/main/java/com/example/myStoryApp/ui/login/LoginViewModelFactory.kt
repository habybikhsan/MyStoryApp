package com.example.myStoryApp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myStoryApp.di.Injection
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(Injection.provideLoginRepository()) as T
        }else
            throw IllegalArgumentException ("View Model of ${modelClass.simpleName} Not Found")
    }
}