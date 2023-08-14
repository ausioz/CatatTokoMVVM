package com.example.catattokomvvm.historygoods

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.catattokomvvm.databinding.ItemHistoryBasicBinding
import com.example.catattokomvvm.databinding.ItemHistoryBinding
import com.example.catattokomvvm.historygoods.edithistory.EditHistoryActivity
import com.example.catattokomvvm.model.GoodsEntity

class HistoryAdapterBasic: RecyclerView.Adapter<HistoryAdapterBasic.ViewHolder>() {
    private val goods = arrayListOf<GoodsEntity>()
    inner class ViewHolder(
        private val binding: ItemHistoryBasicBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goodsEntity: GoodsEntity) {

            binding.id.text = "Id Catatan: " + goodsEntity.id.toString()
            binding.nik.text = "NIK: " + goodsEntity.nik
            binding.nama.text = "Nama: " + goodsEntity.nama
            binding.jumlahBarang.text = "Jumlah Barang: " + goodsEntity.jumlahBarang
            binding.pemasok.text = "Pemasok: " + goodsEntity.pemasok
            binding.tanggal.text = "Tanggal: " + goodsEntity.tanggal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBasicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return goods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(goods[position])
    }

    fun setData(goods: List<GoodsEntity>) {
        this.goods.clear()
        this.goods.addAll(goods)
        notifyDataSetChanged()
    }
}