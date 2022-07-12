package com.example.mystoryapp.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.di.Injection
import com.example.mystoryapp.ui.model.UploadStoryViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class UploadStoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UploadStoryViewModel::class.java))
            UploadStoryViewModel(Injection.provideUploadStoryRepository()) as T
        else
            throw IllegalArgumentException("ViewModel of ${modelClass.simpleName} Not Found")
    }
}