package com.example.stockcryptoapp.feature_login.domain

import com.example.stockcryptoapp.feature_stock_crypto.domain.model.FavoriateStock

data class User (
    var username: String,
    var password: String,
    var favoriteSymbols: List<String>
)