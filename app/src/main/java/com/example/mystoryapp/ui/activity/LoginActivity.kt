package com.example.mystoryapp.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.R
import com.example.mystoryapp.UserPreference
import com.example.mystoryapp.data.RequestLogin
import com.example.mystoryapp.databinding.ActivityLoginBinding
import com.example.mystoryapp.ui.model.LoginViewModel
import com.example.mystoryapp.ui.model.UserViewModel
import com.example.mystoryapp.ui.modelfactory.LoginViewModelFactory
import com.example.mystoryapp.ui.modelfactory.ViewModelFactory

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
        val pref = UserPreference.getInstance(dataStore)
        val userViewModel =ViewModelProvider(this, ViewModelFactory(pref))[UserViewModel::class.java]

        userViewModel.getLoginState().observe(this){state ->
            if (state){
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        with(loginViewModel){
            message.observe(this@LoginActivity){
                val user = userlogin.value
                checkResponseLogin(it, user?.loginResult?.token, isError, userViewModel)
            }
            isLoading.observe(this@LoginActivity){
                showLoading(it)
            }
        }
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
            vm.saveName(loginViewModel.userlogin.value?.loginResult?.name.toString())
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
            val intent =Intent(this,RegisterActivity::class.java)
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