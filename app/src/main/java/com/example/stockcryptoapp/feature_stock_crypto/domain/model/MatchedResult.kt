package com.example.stockcryptoapp.feature_stock_crypto.domain.model

data class MatchedResult(
    val ticker: String,
    val name: String,
    val matchScore: String
)