package com.example.stockcryptoapp.di

import com.example.stockcryptoapp.feature_stock_crypto.data.RepositoryImpl
import com.example.stockcryptoapp.feature_stock_crypto.data.local.StockDao
import com.example.stockcryptoapp.feature_stock_crypto.data.remote.AlphtAdvantageApi
import com.example.stockcryptoapp.feature_stock_crypto.domain.Repository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi() : Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideAlphaAdvantageApi(moshi: Moshi) : AlphtAdvantageApi =
        Retrofit.Builder().baseUrl(AlphtAdvantageApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(AlphtAdvantageApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: AlphtAdvantageApi, stockDao: StockDao): Repository {
        return RepositoryImpl(api, stockDao)
    }
}