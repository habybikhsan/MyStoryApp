package com.example.mystoryapp.ui.main


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import com.example.mystoryapp.adapter.StoryAdapter
import com.example.mystoryapp.data.response.ResponseStory
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.ui.model.StoryViewModel
import com.example.mystoryapp.ui.modelfactory.StoryViewModelFactory
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

        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWZsb3RXcTczZ05wQnlTa1QiLCJpYXQiOjE2NTUzNDg5MDd9.ptbfv0Xz65mAnuUk89vzLdpbyHwye0yY1SZZHktI0r0"
        storyViewModel.getStory(token)

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
        if (isLoading) {
            binding.progressBarStory.visibility = View.VISIBLE
        }else
            binding.progressBarStory.visibility = View.GONE
    }

    companion object{
    }
}