package com.example.stockcryptoapp.feature_login.data

import android.content.Context
import com.example.stockcryptoapp.feature_login.domain.UserStorage
import javax.inject.Inject


class SharedPreferenceStorage @Inject constructor(
    context: Context

) : UserStorage {

    private val SHARED_PREFERENCE_KEY = "com.example.stockcryptoapp.preference"

    private val USERNAME = "username"
    private val FAVORIATE = "user_favorite"

    private val sharedPreferenceStorage = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override fun updateUserFavoriateList(favoriateList: List<String>) {

        val value = favoriateList.joinToString(separator = ",")
        with(sharedPreferenceStorage.edit()){
            putString(FAVORIATE, value)
        }
    }

    override fun retrieveUSerFavoriateList(): List<String> {
        val value = sharedPreferenceStorage.getString(FAVORIATE, "")
        return value?.split(",") ?: emptyList()

    }

}