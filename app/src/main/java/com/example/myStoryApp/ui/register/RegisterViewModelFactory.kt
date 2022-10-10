package com.example.myStoryApp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myStoryApp.di.Injection
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