package com.example.catattokomvvm.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catattokomvvm.data.local.room.GoodsDatabase
import com.example.catattokomvvm.model.GoodsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoodsViewModel(var application: Application) : ViewModel() {
    private val db: GoodsDatabase = GoodsDatabase.getInstance(application)

    internal val goodsHistory : LiveData<List<GoodsEntity>> = db.goodsDao().getGoodsHistory()

    fun recordData(goods: GoodsEntity){
        viewModelScope.launch(Dispatchers.IO){
            db.goodsDao().insertRecord(goods)
        }
    }

    fun updateRecord(goods: GoodsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.goodsDao().updateRecord(goods)
        }

    }

    fun deleteHistory(goods: GoodsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.goodsDao().deleteRecord(goods)
        }
    }
}