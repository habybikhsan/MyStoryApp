package com.example.mystoryapp.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.net.toUri
import com.example.mystoryapp.R
import com.example.mystoryapp.data.response.ResponseStory
import com.example.mystoryapp.databinding.ActivityDetailStoryBinding
import com.example.mystoryapp.utils.convertToTimeAgo
import com.example.mystoryapp.utils.initializeTime4A
import com.example.mystoryapp.utils.loadImageViaGlide

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    private lateinit var story: ResponseStory.ListStoryItem

    @SuppressLint("UseSupportActionBar")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeTime4A()
        story = intent.getParcelableExtra(EXTRA_DATA)!!
        setActionBar(story.name)

        loadImageViaGlide(story.photoUrl.toUri(), binding.imgStory)
        with(binding){
            tvItemName.text = story.name
            tvItemCreatedAt.text = convertToTimeAgo(story.createdAt)
            tvItemDescription.text = getString(R.string.description, story.description)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setActionBar(story: String) {
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.detail_title, story)
        actionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object  {
        const val EXTRA_DATA ="extra_data"

    }
}
