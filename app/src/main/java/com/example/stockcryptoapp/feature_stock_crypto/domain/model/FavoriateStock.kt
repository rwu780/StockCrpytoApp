package com.example.stockcryptoapp.feature_stock_crypto.domain.model

import java.math.BigInteger

data class FavoriateStock(
    val ticker: String,
    val name: String,
    val currentPrice : Float,
    val lastClosePrice: Float,
    val volume: Long,
    val change: Float,
    val changePercent: Float
)
