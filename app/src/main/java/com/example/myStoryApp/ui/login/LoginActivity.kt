package com.example.myStoryApp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.myStoryApp.R
import com.example.myStoryApp.UserPreference
import com.example.myStoryApp.data.RequestLogin
import com.example.myStoryApp.databinding.ActivityLoginBinding
import com.example.myStoryApp.ui.model.UserViewModel
import com.example.myStoryApp.ui.modelfactory.ViewModelFactory
import com.example.myStoryApp.ui.register.RegisterActivity
import com.example.myStoryApp.ui.storyView.StoryViewActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory()
    }
    private var requestLogin : RequestLogin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setButtonLoginEnable()
        setTextChangedListener()
        setAction()
        playAnimation()
        val pref = UserPreference.getInstance(dataStore)
        val userViewModel =ViewModelProvider(this, ViewModelFactory(pref))[UserViewModel::class.java]

        userViewModel.getLoginState().observe(this){state ->
            if (state){
                val intent = Intent(this@LoginActivity, StoryViewActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        with(loginViewModel){
            message.observe(this@LoginActivity){
                val user = userLogin.value
                checkResponseLogin(it, user?.loginResult?.token, isError, userViewModel)
            }
            isLoading.observe(this@LoginActivity){
                showLoading(it)
            }
        }
    }

    @SuppressLint("Recycle")
    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.Title, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailInput, View.ALPHA, 1f).setDuration(500)
        val pass = ObjectAnimator.ofFloat(binding.passwordInput, View.ALPHA, 1f).setDuration(500)
        val seePass = ObjectAnimator.ofFloat(binding.seePassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.buttonLogin,View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerText, View.ALPHA, 1f).setDuration(500)
        val text = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(register, text)
        }

        AnimatorSet().apply {
            playSequentially(title, email, pass, seePass, btnLogin, together)
            start()
        }


        ObjectAnimator.ofFloat(binding.Title, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun checkResponseLogin(
        msg: String?,
        token: String?,
        isError: Boolean,
        vm: UserViewModel
    ) {
        if (!isError) {
            Toast.makeText(
                this,
                "${getString(R.string.success_login)} $msg",
                Toast.LENGTH_LONG
            ).show()
            vm.saveLoginState(true)
            if (token != null) vm.saveToken(token)
            vm.saveName(loginViewModel.userLogin.value?.loginResult?.name.toString())
        } else {
            when (msg) {
                "Unauthorized" -> {
                    Toast.makeText(this, getString(R.string.unauthorized), Toast.LENGTH_SHORT)
                        .show()
                    binding.emailInput.apply {
                        setText("")
                        requestFocus()
                    }
                    binding.passwordInput.setText("")

                }
                "timeout" -> {
                    Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(
                        this,
                        "${getString(R.string.error_message)} $msg",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun isDataValid(): Boolean {
        return binding.passwordInput.isPassValid
    }

    private fun setTextChangedListener() {
        binding.emailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButtonLoginEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.passwordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButtonLoginEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setAction() {
        binding.buttonLogin.setOnClickListener {
            binding.emailInput.clearFocus()
            binding.passwordInput.clearFocus()

            if (isDataValid()){
                requestLogin = RequestLogin(
                    binding.emailInput.text.toString().trim(),
                    binding.passwordInput.text.toString().trim()
                )
                loginViewModel.login(requestLogin!!)
            }
        }

        binding.seePassword.setOnClickListener{
            binding.passwordInput.transformationMethod =
                if (binding.seePassword.isChecked) {
                    HideReturnsTransformationMethod.getInstance()
                }else {
                    PasswordTransformationMethod.getInstance()
                }
        }
        binding.registerText.setOnClickListener {
            val intent =Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setButtonLoginEnable() {
        binding.buttonLogin.isEnabled = binding.emailInput.text != null &&
                binding.emailInput.text.toString().isNotEmpty() &&
                binding.passwordInput.text != null &&
                binding.passwordInput.text.toString().isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}