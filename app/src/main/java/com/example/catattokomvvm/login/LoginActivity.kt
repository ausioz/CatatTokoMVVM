package com.example.catattokomvvm.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.MainActivity
import com.example.catattokomvvm.databinding.ActivityLoginBinding
import com.example.catattokomvvm.di.ViewModelFactory
import com.example.catattokomvvm.viewmodel.LoginViewModel



class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this, ViewModelFactory(application))[LoginViewModel::class.java]

        loginViewModel.getUserData().observe(this, Observer {
            if (it != null) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })

        binding.loginBT.setOnClickListener {
            val mail = binding.mailET.text.toString()
            val pwd = binding.pwdET.text.toString()

            if (mail.isNotEmpty() && pwd.isNotEmpty()){
                loginViewModel.signIn(mail,pwd)
            }
            else{
                Toast.makeText(this, "Please input Mail and/or Password", Toast.LENGTH_SHORT).show()
            }
        }




    }
}