package com.example.stockcryptoapp.feature_login.domain

data class User (
    var username: String,
    var favoriteSymbols: Set<String>? = null
)