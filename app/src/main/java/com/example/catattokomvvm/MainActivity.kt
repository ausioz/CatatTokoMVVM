package com.example.catattokomvvm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.databinding.ActivityMainBinding
import com.example.catattokomvvm.di.ViewModelFactory
import com.example.catattokomvvm.historygoods.HistoryActivity
import com.example.catattokomvvm.historygoods.HistoryActivityBasic
import com.example.catattokomvvm.recordgoods.RecordGoodsActivity
import com.example.catattokomvvm.viewmodel.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this,ViewModelFactory(application))[LoginViewModel::class.java]


        loginViewModel.getUserRole().observe(this) {
            if (it == "admin") {
                binding.btCatat.setBackgroundColor(getColor(R.color.teal_700))
                binding.btCatat.isClickable = true

                binding.btCatat.setOnClickListener {
                    startActivity(Intent(applicationContext, RecordGoodsActivity::class.java))
                }

                binding.btHistori.setOnClickListener {
                    startActivity(Intent(applicationContext, HistoryActivity::class.java))
                }
            }
            if (it == "basic") {
                binding.btCatat.setBackgroundColor(getColor(androidx.constraintlayout.widget.R.color.material_grey_600))
                binding.btCatat.isClickable = false

                binding.btHistori.setOnClickListener {
                    startActivity(Intent(applicationContext, HistoryActivityBasic::class.java))
                }
            }
        }
        loginViewModel.userName.observe(this) {
            binding.tvWelcome.text = "Selamat datang, $it"
        }


        binding.btSignout.setOnClickListener {
            loginViewModel.signOut()
            finish()
        }

    }
}