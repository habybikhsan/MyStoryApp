package com.example.mystoryapp.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.di.Injection
import com.example.mystoryapp.ui.model.RegisterViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(Injection.provideRegisterRepository()) as T
        }else
            throw IllegalArgumentException ("View Model of ${modelClass.simpleName} Not Found")
    }
}