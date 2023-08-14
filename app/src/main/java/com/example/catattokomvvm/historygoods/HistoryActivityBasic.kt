package com.example.catattokomvvm.historygoods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catattokomvvm.viewmodel.GoodsViewModel
import com.example.catattokomvvm.data.local.room.GoodsDatabase
import com.example.catattokomvvm.databinding.ActivityHistoryBasicBinding
import com.example.catattokomvvm.databinding.ActivityHistoryBinding
import com.example.catattokomvvm.di.ViewModelFactory

class HistoryActivityBasic : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBasicBinding
    private lateinit var historyAdapterBasic: HistoryAdapterBasic
    private lateinit var goodsViewModel: GoodsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goodsViewModel =
            ViewModelProvider(this, ViewModelFactory(application))[GoodsViewModel::class.java]

        goodsViewModel.goodsHistory.observe(this, Observer {
            historyAdapterBasic.setData(it)
        })

        binding.recycleView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        historyAdapterBasic = HistoryAdapterBasic()
        binding.recycleView.adapter = historyAdapterBasic


    }

}