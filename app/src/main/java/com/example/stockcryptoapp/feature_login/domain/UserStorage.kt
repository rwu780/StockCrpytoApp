package com.example.stockcryptoapp.feature_login.domain

interface UserStorage {

    fun updateUser(user: User?)
    fun retrieveUser(username: String, password: String) : User?
    fun isValidUser(username: String): Boolean
    fun registerUser(username: String, password: String): User?


}