package com.example.stockcryptoapp.feature_stock_crypto.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

@Entity(tableName = "stock")
data class Stock(
    @PrimaryKey val ticker: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "currentPrice") val currentPrice : String = "0.0",
    @ColumnInfo(name = "lastClosePrice") val lastClosePrice: String = "0.0",
    @ColumnInfo(name = "volume") val volume: String = "0.0",
    @ColumnInfo(name = "change") val change: String = "0.0",
    @ColumnInfo(name = "changePercent") val changePercent: String ="0.0"
)
