package com.example.mystoryapp.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.di.Injection
import com.example.mystoryapp.ui.model.LoginViewModel
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