package com.example.catattokomvvm.recordgoods

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.catattokomvvm.data.local.room.GoodsDatabase
import com.example.catattokomvvm.databinding.ActivityRecordGoodsBinding
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.viewmodel.GoodsViewModel
import com.example.catattokomvvm.di.ViewModelFactory
import com.example.catattokomvvm.login.LoginActivity
import com.example.catattokomvvm.model.GoodsEntity
import com.example.catattokomvvm.viewmodel.LoginViewModel


class RecordGoodsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordGoodsBinding
    private lateinit var goodsViewModel: GoodsViewModel
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordGoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        goodsViewModel =
            ViewModelProvider(this, ViewModelFactory(application))[GoodsViewModel::class.java]
//        goodsDatabase = GoodsDatabase.getInstance(this)

        loginViewModel = ViewModelProvider(this,ViewModelFactory(application))[LoginViewModel::class.java]

        binding.btRecord.setOnClickListener {
            recordGoods()
        }

        loginViewModel.userName.observe(this) {
            binding.etNama.setText(loginViewModel.userName.value)
        }
        loginViewModel.nik.observe(this) {
            binding.etNik.setText(loginViewModel.nik.value)
        }


    }

    private fun recordGoods() {
        val nik = binding.etNik.text.toString()
        val nama = binding.etNama.text.toString()
        val jumlah = binding.etJumlah.text.toString()
        val pemasok = binding.etPemasok.text.toString()
        val tanggal = binding.etTanggal.text.toString()

        if (inputCheck(nik, nama, jumlah, pemasok, tanggal)) {
            AlertDialog.Builder(this).setTitle("Catat Barang")
                .setMessage("Apakah anda yakin dengan pengisian catatan?")
                .setPositiveButton("Ya") { _, _ ->
                    goodsViewModel.recordData(
                        GoodsEntity(0, nik, nama, jumlah, pemasok, tanggal)
                    )
                    Toast.makeText(this, "Catatan Tersimpan", Toast.LENGTH_SHORT).show()
                    finish()
                }.setNegativeButton("Kembali") { _, _ -> }.show()
        } else {
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
        return
    }

    private fun inputCheck(
        nik: String, nama: String, jumlah: String, pemasok: String, tanggal: String
    ): Boolean {
        return !(TextUtils.isEmpty(nik) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(jumlah) || TextUtils.isEmpty(
            pemasok
        ) || TextUtils.isEmpty(tanggal))
    }

}
