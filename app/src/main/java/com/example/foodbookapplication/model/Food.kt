package com.example.foodbookapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Food(
    @ColumnInfo(name = "isim")
    val isim: String,
    @ColumnInfo(name = "gorsel")
    val gorsel: String,
    @ColumnInfo(name = "kalori")
    val kalori: String,
    @ColumnInfo(name = "karbonhidrat")
    val karbonhidrat: String,
    @ColumnInfo(name = "protein")
    val protein: String,
    @ColumnInfo(name = "yag")
    val yag: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}