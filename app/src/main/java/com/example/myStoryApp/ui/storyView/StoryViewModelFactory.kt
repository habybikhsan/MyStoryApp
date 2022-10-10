package com.example.myStoryApp.ui.storyView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myStoryApp.di.Injection
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