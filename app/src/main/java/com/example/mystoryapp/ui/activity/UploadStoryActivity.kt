package com.example.mystoryapp.ui.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.R
import com.example.mystoryapp.UserPreference
import com.example.mystoryapp.databinding.ActivityUploadStoryBinding
import com.example.mystoryapp.ui.model.UserViewModel
import com.example.mystoryapp.ui.modelfactory.ViewModelFactory
import com.example.mystoryapp.utils.createCustomTempFile
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class UploadStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadStoryBinding
    private lateinit var token: String
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            val result = BitmapFactory.decodeFile(myFile.path)

            binding.imgStory.setImageBitmap(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        val pref = UserPreference.getInstance(dataStore)
        val userViewModel = ViewModelProvider(this, ViewModelFactory(pref))[UserViewModel::class.java]

        userViewModel.getToken().observe(this){
            token = it
        }
        userViewModel.getName().observe(this){
            binding.tvName.text = StringBuilder(getString(R.string.post_as)).append(" ").append(it)
        }

        binding.imgStory.setOnClickListener {
            select()
        }
    }

    private fun select() {
        val items = arrayOf<CharSequence>(

            getString(R.string.from_galeri),
            getString(R.string.take_picture),
            getString(R.string.cancel)
        )

        val title = TextView(this)
        title.text = getString(R.string.select_photo)
        title.gravity = Gravity.CENTER
        title.setPadding(10, 15, 15, 10)
        title.textSize = 22f
        val builder = AlertDialog.Builder(
            this
        )
        builder.setCustomTitle(title)
        builder.setItems(items) { dialog, item ->
            when {
                items[item] == getString(R.string.from_galeri) -> {
                    startGallery()

                }
                items[item] == getString(R.string.take_picture) -> {
                    startTakePhoto()

                }
                items[item] == getString(R.string.cancel) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@UploadStoryActivity,
                getString(R.string.package_name),
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }
    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            getFile = myFile
            binding.imgStory.setImageURI(selectedImg)
            binding.desText.requestFocus()
        }
    }
    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    @SuppressLint("RestrictedApi")
    private fun setActionBar(){
        supportActionBar?.title = getString(R.string.add_story)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val FILENAME_FORMAT = "MMddyyyy"
    }
}