package com.example.mystoryapp.ui.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.UserPreference
import com.example.mystoryapp.adapter.StoryAdapter
import com.example.mystoryapp.data.response.ResponseStory
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.ui.model.StoryViewModel
import com.example.mystoryapp.ui.model.UserViewModel
import com.example.mystoryapp.ui.modelfactory.StoryViewModelFactory
import com.example.mystoryapp.ui.modelfactory.ViewModelFactory
import com.example.mystoryapp.utils.initializeTime4A

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var token: String

    private val storyViewModel : StoryViewModel by viewModels {
        StoryViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        initializeTime4A()

        val pref = UserPreference.getInstance(dataStore)
        val userViewModel = ViewModelProvider(this, ViewModelFactory(pref))[UserViewModel::class.java]

        userViewModel.getToken().observe(this){
            token = it
            storyViewModel.getStory(token)
        }


        storyViewModel.message.observe(this){
            setStory(storyViewModel.storiess)
        }

        storyViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setStory(story: List<ResponseStory.ListStoryItem>) {
        val listUserAdapter = StoryAdapter(story)
        binding.recyclerView.adapter = listUserAdapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarStory.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}