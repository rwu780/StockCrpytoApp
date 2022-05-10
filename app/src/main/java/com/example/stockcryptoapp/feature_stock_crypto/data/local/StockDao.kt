package com.example.stockcryptoapp.feature_stock_crypto.data.local

import androidx.room.*
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.Stock

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompany(company: CompanySummary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: Stock)

    @Query("SELECT * from company where symbol = :symbol")
    suspend fun getCompany(symbol: String) : CompanySummary?

    @Query("SELECT * from stock where ticker = :symbol")
    suspend fun getStock(symbol: String) : Stock?

    @Query("SELECT name from company where symbol = :symbol")
    suspend fun getCompanyName(symbol: String) : String?


}