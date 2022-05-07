package com.example.stockcryptoapp.di

import android.content.Context
import com.example.stockcryptoapp.feature_login.data.SharedPreferenceStorage
import com.example.stockcryptoapp.feature_login.domain.UserStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideUserStorage(@ApplicationContext cxt: Context): UserStorage {
        return SharedPreferenceStorage(cxt)
    }
}