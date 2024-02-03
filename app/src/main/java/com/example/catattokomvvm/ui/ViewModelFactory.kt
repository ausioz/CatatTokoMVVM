package com.example.catattokomvvm.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.ui.goods.GoodsViewModel
import com.example.catattokomvvm.ui.login.LoginViewModel

class ViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass) {
            GoodsViewModel::class.java -> GoodsViewModel(application) as T
            LoginViewModel::class.java -> LoginViewModel(application) as T
            else -> {throw UnsupportedOperationException()}
        }
    }

}