package com.example.stockcryptoapp.feature_stock_crypto.domain.model

// https://eodhistoricaldata.com/img/logos/US/fb.png

data class CompanySummary(
    val symbol: String,
    val name: String,
    val description: String,
    val industry: String,
    val address: String
)

