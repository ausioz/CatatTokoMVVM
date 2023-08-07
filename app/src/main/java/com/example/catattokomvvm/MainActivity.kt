package com.example.catattokomvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catattokomvvm.databinding.ActivityMainBinding
import com.example.catattokomvvm.historygoods.HistoryActivity
import com.example.catattokomvvm.recordgoods.RecordGoodsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCatat.setOnClickListener {
            startActivity(Intent(applicationContext, RecordGoodsActivity::class.java))
        }

        binding.btHistori.setOnClickListener {
            startActivity(Intent(applicationContext, HistoryActivity::class.java))
        }

        binding.btKeluar.setOnClickListener {
            finish()
        }

    }
}