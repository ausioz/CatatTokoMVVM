package com.example.catattokomvvm.data.local.room.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Update
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate

@Entity(tableName = "goods")
@Parcelize
data class GoodsEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int,
    @ColumnInfo(name = "nik")
    var nik : String,
    @ColumnInfo(name = "nama")
    var nama : String,
    @ColumnInfo(name = "jumlahBarang")
    var jumlahBarang : String,
    @ColumnInfo(name = "pemasok")
    var pemasok : String,
    @ColumnInfo(name = "tanggal")
    var tanggal : String
):Parcelable
