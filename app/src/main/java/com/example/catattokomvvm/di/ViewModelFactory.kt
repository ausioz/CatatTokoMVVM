package com.example.catattokomvvm.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.GoodsViewModel

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            GoodsViewModel::class.java -> GoodsViewModel(application) as T
            else -> {throw UnsupportedOperationException()}
        }
    }

}