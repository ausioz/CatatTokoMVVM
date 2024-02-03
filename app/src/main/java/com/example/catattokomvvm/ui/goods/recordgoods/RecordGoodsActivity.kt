package com.example.catattokomvvm.ui.goods.recordgoods

import android.icu.util.Calendar
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.catattokomvvm.databinding.ActivityRecordGoodsBinding
import androidx.lifecycle.ViewModelProvider
import com.example.catattokomvvm.R
import com.example.catattokomvvm.ui.goods.GoodsViewModel
import com.example.catattokomvvm.ui.ViewModelFactory
import com.example.catattokomvvm.data.local.room.model.GoodsEntity
import com.example.catattokomvvm.ui.login.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

        loginViewModel =
            ViewModelProvider(this, ViewModelFactory(application))[LoginViewModel::class.java]

        binding.etTanggal.doAfterTextChanged {
            if (isDateInputValid()) {
                binding.btRecord.setBackgroundColor(getColor(R.color.teal_700))
                binding.btRecord.isClickable = true
            } else {
                binding.etTanggal.error = "Format Tanggal Salah"
                binding.btRecord.setBackgroundColor(getColor(androidx.appcompat.R.color.primary_dark_material_light))
                binding.btRecord.isClickable = false
            }
        }

        binding.btRecord.setOnClickListener {
            recordGoods()
        }

        loginViewModel.userName.observe(this) {
            binding.etNama.setText(loginViewModel.userName.value)
        }
        loginViewModel.nik.observe(this) {
            binding.etNik.setText(loginViewModel.nik.value)
        }

        binding.etTanggal.setText(getCurrentDate())

    }

    private fun recordGoods() {
        val nik = binding.etNik.text.toString()
        val nama = binding.etNama.text.toString()
        val jumlah = binding.etJumlah.text.toString()
        val pemasok = binding.etPemasok.text.toString()
        val tanggal = binding.etTanggal.text.toString()

        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        val lastDate =
            if (goodsViewModel.getDateRecord().isEmpty()) dateFormat.parse("0000/00/00") as Date
            else dateFormat.parse(goodsViewModel.getDateRecord().first()) as Date

        val inputDate = dateFormat.parse(tanggal)


        if (inputCheck(nik, nama, jumlah, pemasok, tanggal)) {
            if (daysBetween(lastDate, inputDate!!) < 14) {
                //<editor-fold desc="Alert Dialog 1" defaultstate="collapsed">
                AlertDialog.Builder(this).setTitle("Tangal Bermasalah")
                    .setMessage("Tanggal input dengan catatan terakhir kurang dari 14 hari")
                    .setPositiveButton("Lanjut") { _, _ ->
                        //<editor-fold desc="Alert Dialog 2" defaultstate="collapsed">
                        AlertDialog.Builder(this).setTitle("Catat Barang")
                            .setMessage("Apakah anda yakin dengan pengisian catatan?")
                            .setPositiveButton("Ya") { _, _ ->
                                goodsViewModel.recordData(
                                    GoodsEntity(0, nik, nama, jumlah, pemasok, tanggal)
                                )
                                Toast.makeText(this, "Catatan Tersimpan", Toast.LENGTH_SHORT).show()
                                finish()
                            }.setNegativeButton("Kembali") { _, _ -> }.show()
                        //</editor-fold>
                    }.setNegativeButton("Kembali") { _, _ -> }.show()
                //</editor-fold>
            } else {
                AlertDialog.Builder(this).setTitle("Catat Barang")
                    .setMessage("Apakah anda yakin dengan pengisian catatan?")
                    .setPositiveButton("Ya") { _, _ ->
                        goodsViewModel.recordData(
                            GoodsEntity(0, nik, nama, jumlah, pemasok, tanggal)
                        )
                        Toast.makeText(this, "Catatan Tersimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    }.setNegativeButton("Kembali") { _, _ -> }.show()
            }
        } else {
            Toast.makeText(this, "Catatan belum terisi lengkap", Toast.LENGTH_SHORT).show()
        }
        return
    }

    private fun isDateInputValid(): Boolean {
        // yyyy/mm/dd regex closest match to calendar
        return binding.etTanggal.text?.matches(("^\\d{4}\\/(0?[1-9]|1[012])\\/(0?[1-9]|[12][0-9]|3[01])\$").toRegex()) == true
    }


    private fun daysBetween(d1: Date, d2: Date): Int {
        return ((d2.time - d1.time) / (1000 * 60 * 60 * 24)).toInt()
    }
    private fun getCurrentDate(): String {
        val todayTime: Date = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        return dateFormat.format(todayTime)
    }

    private fun inputCheck(
        nik: String, nama: String, jumlah: String, pemasok: String, tanggal: String
    ): Boolean {
        return !(TextUtils.isEmpty(nik) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(jumlah) || TextUtils.isEmpty(
            pemasok
        ) || TextUtils.isEmpty(tanggal))
    }

}
