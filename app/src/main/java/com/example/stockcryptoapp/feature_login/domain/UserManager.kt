package com.example.stockcryptoapp.feature_login.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val storage: UserStorage
) {

    private var user: User? = null

    fun isUserLoggedIn() = user != null

    fun setUserFavoriateList(favoriateList: List<String>) {
        storage.updateUserFavoriateList(favoriateList)
        user?.favoriteSymbols = favoriateList
    }

    fun getUserFavoriateList() = user?.favoriteSymbols ?: listOf("AAPL","MSFT", "FB", "SQ", "CHWY")

    fun logout() {
        user = null
    }

}