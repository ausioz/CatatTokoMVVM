package com.example.catattokomvvm.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.R
import com.example.catattokomvvm.databinding.ActivityMainBinding
import com.example.catattokomvvm.ui.goods.historygoods.HistoryActivity
import com.example.catattokomvvm.ui.goods.historygoods.HistoryActivityBasic
import com.example.catattokomvvm.ui.login.LoginViewModel
import com.example.catattokomvvm.ui.goods.recordgoods.RecordGoodsActivity
import com.example.catattokomvvm.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    private val loadingDialog = LoadingDialogFragment()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel =
            ViewModelProvider(this, ViewModelFactory(application))[LoginViewModel::class.java]


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

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }

        loginViewModel.userName.observe(this) {
            binding.tvWelcome.text = "Selamat datang, $it"
        }


        binding.btSignout.setOnClickListener {
            loginViewModel.signOut()
            Toast.makeText(this, "Signout sukses", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }

    }

    private fun showLoading(isLoading: Boolean) {
        loadingDialog.isCancelable = false
        if (isLoading) {
            loadingDialog.show(supportFragmentManager, "loadingDialog")
            binding.btCatat.visibility = View.GONE
            binding.btHistori.visibility = View.GONE
            binding.btSignout.visibility = View.GONE
            binding.tvWelcome.visibility = View.GONE
        } else {
            if (loadingDialog.isVisible) loadingDialog.dismiss()
            binding.btCatat.visibility = View.VISIBLE
            binding.btHistori.visibility = View.VISIBLE
            binding.btSignout.visibility = View.VISIBLE
            binding.tvWelcome.visibility = View.VISIBLE
        }
    }
}