package com.example.stockcryptoapp.feature_login.domain

interface UserStorage {

    fun updateUserFavoriateList(favoriateList: List<String>)
    fun retrieveUSerFavoriateList() : List<String>

}