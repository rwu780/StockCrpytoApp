package com.example.stockcryptoapp.di

import android.content.Context
import androidx.room.Room
import com.example.stockcryptoapp.feature_stock_crypto.data.local.StockDao
import com.example.stockcryptoapp.feature_stock_crypto.data.local.StockDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext cxt: Context): StockDatabase {
        return Room.databaseBuilder(
            cxt,
            StockDatabase::class.java,
            StockDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideStockDao(stockDatabase: StockDatabase) : StockDao {
        return stockDatabase.stockDao()
    }


}