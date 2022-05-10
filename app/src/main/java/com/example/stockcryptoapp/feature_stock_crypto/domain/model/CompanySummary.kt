package com.example.stockcryptoapp.feature_stock_crypto.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// https://eodhistoricaldata.com/img/logos/US/fb.png

@Entity(tableName = "company")
data class CompanySummary(
    @PrimaryKey val symbol: String,
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="description") val description: String,
    @ColumnInfo(name="industry") val industry: String,
    @ColumnInfo(name="address") val address: String
){

}

