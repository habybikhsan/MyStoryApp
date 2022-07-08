package com.example.mystoryapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import com.example.mystoryapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setButtonLoginEnable()
        setTextChangedListener()
        setAction()

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
            Toast.makeText(this@LoginActivity,
                binding.emailInput.text,
                Toast.LENGTH_SHORT).show()
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
}