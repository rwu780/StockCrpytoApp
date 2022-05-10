package com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject


import com.squareup.moshi.Json


data class QuoteResponse(
    @Json(name = "Global Quote")
    val globalQuote: GlobalQuote
)