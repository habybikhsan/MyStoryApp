package com.example.mystoryapp.ui.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.R
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
    private var isFinished = false

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
            showToast(it)
        }

        storyViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showToast(msg: String?) {
        if (storyViewModel.isError && !isFinished) {
            Toast.makeText(
                this,
                "${getString(R.string.error_load)} $msg",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setStory(story: List<ResponseStory.ListStoryItem>) {
        val listUserAdapter = StoryAdapter(story)
        binding.recyclerView.adapter = listUserAdapter

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarStory.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout){
            showAlertDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        val alert = builder.create()
        builder
            .setTitle(getString(R.string.logout))
            .setMessage(getString(R.string.you_sure))
            .setPositiveButton(getString(R.string.no)) { _, _ ->
                alert.cancel()
            }
            .setNegativeButton(getString(R.string.yes)) { _, _ ->
                logout()
            }
            .show()
    }

    private fun logout() {
        val pref = UserPreference.getInstance(dataStore)
        val userViewModel = ViewModelProvider(this, ViewModelFactory(pref))[UserViewModel::class.java]

        userViewModel.apply {
            saveLoginState(false)
            saveToken("")
            saveName("")
        }
        isFinished = true
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}