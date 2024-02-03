package com.example.catattokomvvm.ui.goods.historygoods

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catattokomvvm.databinding.ItemHistoryBinding
import com.example.catattokomvvm.ui.goods.historygoods.edithistory.EditHistoryActivity
import com.example.catattokomvvm.data.local.room.model.GoodsEntity

class HistoryAdapter(
    private val onClickDelete: (GoodsEntity) -> Unit,
//    private val onClickUpdate: (GoodsEntity) -> Unit,
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private val goods = arrayListOf<GoodsEntity>()
    inner class ViewHolder(
        private val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(goodsEntity: GoodsEntity) {

            binding.id.text = "Id Catatan: " + goodsEntity.id.toString()
            binding.nik.text = "NIK: " + goodsEntity.nik
            binding.nama.text = "Nama: " + goodsEntity.nama
            binding.jumlahBarang.text = "Jumlah Barang: " + goodsEntity.jumlahBarang
            binding.pemasok.text = "Pemasok: " + goodsEntity.pemasok
            binding.tanggal.text = "Tanggal: " + goodsEntity.tanggal


            binding.delete.setOnClickListener { onClickDelete(goodsEntity) }
            binding.edit.setOnClickListener { setListeners(goods[adapterPosition], holder = ViewHolder(binding)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHistoryBinding.inflate(
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

    private fun setListeners(selectedHistory: GoodsEntity, holder: ViewHolder){
            val intent = Intent(holder.itemView.context, EditHistoryActivity::class.java)
            // pass record to next activity
            intent.putExtra("record", selectedHistory)
            holder.itemView.context.startActivity(intent)
    }
}