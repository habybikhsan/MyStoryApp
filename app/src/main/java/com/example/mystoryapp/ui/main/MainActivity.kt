package com.example.mystoryapp.ui.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import com.example.mystoryapp.data.Story
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.ui.model.StoryViewModel
import com.example.mystoryapp.ui.modelfactory.StoryViewModelFactory
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    private var callApiHandler: Handler? = null
    private lateinit var binding: ActivityMainBinding

    private val storyViewModel : StoryViewModel by viewModels {
        StoryViewModelFactory()
    }

    override fun onDestroy() {
        super.onDestroy()
        callApiHandler?.removeCallbacksAndMessages(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

        callApiHandler = Handler(Looper.getMainLooper())
        callApiHandler?.postDelayed({
            loadData()
        }, DELAY_CALL_API)
    }

    private fun loadData() {
        TODO("Not yet implemented")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarStory.visibility = View.VISIBLE
        }else
            binding.progressBarStory.visibility = View.GONE
    }

    companion object{
        private const val DELAY_CALL_API: Long = 2000
    }
}