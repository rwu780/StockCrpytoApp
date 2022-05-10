package com.example.stockcryptoapp.feature_stock_crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.Stock

@Database(entities = [Stock::class, CompanySummary::class], version = 1)
abstract class StockDatabase : RoomDatabase() {

    abstract fun stockDao(): StockDao

    companion object {
        val DATABASE_NAME = "stock_database"
    }
}