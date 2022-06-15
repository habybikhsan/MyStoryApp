package com.example.mystoryapp.ui.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.di.Injection
import com.example.mystoryapp.ui.model.StoryViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class StoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(StoryViewModel::class.java))
            StoryViewModel(Injection.provideStoryRepository()) as T
        else
            throw IllegalArgumentException("ViewModel of ${modelClass.simpleName} Not Found")
    }
}