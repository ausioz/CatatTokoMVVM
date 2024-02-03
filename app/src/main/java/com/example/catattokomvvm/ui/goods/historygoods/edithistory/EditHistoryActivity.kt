package com.example.catattokomvvm.ui.goods.historygoods.edithistory

import android.content.Intent

import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.ui.goods.GoodsViewModel

import com.example.catattokomvvm.databinding.ActivityEditHistoryBinding
import com.example.catattokomvvm.ui.ViewModelFactory

import com.example.catattokomvvm.data.local.room.model.GoodsEntity

class EditHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditHistoryBinding
    private lateinit var goodsViewModel: GoodsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goodsViewModel = ViewModelProvider(this, ViewModelFactory(application))[GoodsViewModel::class.java]

        val parcel = intent.parcelable<GoodsEntity>("record")

        binding.etNik.setText(parcel?.nik)
        binding.etNama.setText(parcel?.nama)
        binding.etJumlah.setText(parcel?.jumlahBarang)
        binding.etPemasok.setText(parcel?.pemasok)
        binding.etTanggal.setText(parcel?.tanggal)

        binding.btRecord.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Catat Barang")
                .setMessage("Apakah anda yakin dengan pengisian catatan?")
                .setPositiveButton("Ya") { _, _ ->
                    updateRecord()
                    Toast.makeText(this, "Catatan Tersimpan", Toast.LENGTH_LONG).show()
                    finish()
                }
                .setNegativeButton("Kembali"){_,_ ->}
                .show()
        }

    }

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): GoodsEntity? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, GoodsEntity::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? GoodsEntity
    }

    private fun updateRecord() {
        val parcel = intent.parcelable<GoodsEntity>("record")
        val currentId = parcel?.id ?:0

        val nik = binding.etNik.text.toString()
        val nama = binding.etNama.text.toString()
        val jumlah = binding.etJumlah.text.toString()
        val pemasok = binding.etPemasok.text.toString()
        val tanggal = binding.etTanggal.text.toString()

        if (inputCheck(nik, nama, pemasok, jumlah, tanggal)){
            val updatedRecord = GoodsEntity(currentId,nik,nama,jumlah,pemasok,tanggal)
            goodsViewModel.updateRecord(updatedRecord)
        }
        else{
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(
        nik: String, nama: String, jumlah: String,pemasok: String, tanggal: String
    ): Boolean {
        return !(TextUtils.isEmpty(nik) || TextUtils.isEmpty(nama)
                || TextUtils.isEmpty(jumlah) || TextUtils.isEmpty(pemasok)  ||
                TextUtils.isEmpty(tanggal))
    }

}