package com.example.stockcryptoapp.feature_login.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(
    private val storage: UserStorage
) {

    private var user: User? = null
    private val defaultMutableSet: MutableSet<String> = mutableSetOf("AAPL", "MSFT", "GOOG","FB", "TSLA", "SQ", "NET", "PYPL", "AMD", "NVDA")

    fun userLogIn(username: String, password: String): User? {

        val retrieveUser = storage.retrieveUser(username, password)

        retrieveUser?.let {
            user = it
        }

        return retrieveUser

    }

    fun registerUser(username: String, password: String): Boolean {

        user = storage.registerUser(username, password)
        return isUserLoggedIn()
    }

    fun isUserExist(username: String): Boolean {
        return storage.isValidUser(username)
    }

    fun updateUserFavoriate(symbols: Set<String>){
        user?.favoriteSymbols = symbols
        storage.updateUser(user)
    }

    fun retrieveUserFavorite(): Set<String>{
        return user?.favoriteSymbols ?: defaultMutableSet
    }

    fun isUserLoggedIn() = user != null

    fun logout() {
        user = null
    }

}