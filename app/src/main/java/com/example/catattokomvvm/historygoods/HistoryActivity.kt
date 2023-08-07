package com.example.catattokomvvm.historygoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catattokomvvm.GoodsViewModel
import com.example.catattokomvvm.data.local.room.GoodsDatabase
import com.example.catattokomvvm.databinding.ActivityHistoryBinding
import com.example.catattokomvvm.di.ViewModelFactory

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var goodsDatabase: GoodsDatabase
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var goodsViewModel: GoodsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goodsViewModel =
            ViewModelProvider(this, ViewModelFactory(application))[GoodsViewModel::class.java]

        goodsViewModel.goodsHistory.observe(this, Observer {
            historyAdapter.setData(it)
        })

        binding.recycleView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        historyAdapter = HistoryAdapter(
//            onClickUpdate = ::updateUser,
            onClickDelete = goodsViewModel::deleteHistory
        )
        binding.recycleView.adapter = historyAdapter


    }

}