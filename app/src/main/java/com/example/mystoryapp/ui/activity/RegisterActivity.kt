package com.example.mystoryapp.ui.activity

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
import com.example.mystoryapp.R
import com.example.mystoryapp.data.RequestRegister
import com.example.mystoryapp.databinding.ActivityRegisterBinding
import com.example.mystoryapp.ui.model.RegisterViewModel
import com.example.mystoryapp.ui.modelfactory.RegisterViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var requestRegister : RequestRegister? = null
    private val registerViewModel : RegisterViewModel by viewModels {
        RegisterViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setButtonLoginEnable()
        setTextChangedListener()
        setAction()

        with(registerViewModel){
            message.observe(this@RegisterActivity){
                checkResponseRegister(it, isError)
            }
            isLoading.observe(this@RegisterActivity){
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        binding.progressBar.visibility = if (isLoading == true) View.VISIBLE else View.GONE
    }

    private fun checkResponseRegister(msg: String?, isError: Boolean) {
        if (!isError) {

            Toast.makeText(
                this,
                "${getString(R.string.user_created)} $msg",
                Toast.LENGTH_LONG
            ).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }else {
            when (msg) {
                "Bad Request" -> {
                    Toast.makeText(this, getString(R.string.email_taken), Toast.LENGTH_SHORT).show()
                    binding.emailInput.apply {
                        setText("")
                        requestFocus()
                    }
                }
                "timeout" -> {
                    Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT)
                        .show()
                }
                "Email is already taken" -> {
                    Toast.makeText(this, getString(R.string.email_taken), Toast.LENGTH_SHORT).show()
                    binding.emailInput.apply {
                        setText("")
                        requestFocus()
                    }
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

    private fun setAction() {
        with(binding){
            buttonRegister.setOnClickListener {
                emailInput.clearFocus()
                passwordInput.clearFocus()
                nameInput.clearFocus()

                if (isDataValid()){
                    requestRegister = RequestRegister(
                        emailInput.text.toString().trim(),
                        passwordInput.text.toString().trim(),
                        nameInput.text.toString().trim()
                    )
                    registerViewModel.getResponseRegister(requestRegister!!)
                }
            }

            seePassword.setOnClickListener{
                passwordInput.transformationMethod =
                    if (seePassword.isChecked) {
                        HideReturnsTransformationMethod.getInstance()
                    }else {
                        PasswordTransformationMethod.getInstance()
                    }
            }
        }
    }

    private fun isDataValid(): Boolean {
        return binding.passwordInput.isPassValid
    }

    private fun setButtonLoginEnable() {
        with(binding){
            buttonRegister.isEnabled = emailInput.text != null &&
                emailInput.text.toString().isNotEmpty() &&
                passwordInput.text != null &&
                passwordInput.text.toString().isNotEmpty()&&
                nameInput.text != null &&
                passwordInput.text.toString().isNotEmpty()
        }
    }

    private fun setTextChangedListener() {
        with(binding){
            emailInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    setButtonLoginEnable()
                }
                override fun afterTextChanged(s: Editable) {
                }
            })
            passwordInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    setButtonLoginEnable()
                }
                override fun afterTextChanged(s: Editable) {
                }
            })
            nameInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    setButtonLoginEnable()
                }
                override fun afterTextChanged(s: Editable) {
                }
            })
        }
    }
}