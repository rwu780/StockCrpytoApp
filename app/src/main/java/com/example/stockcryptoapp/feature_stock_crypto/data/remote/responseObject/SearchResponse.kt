package com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SearchTickerResponse(
    @Json(name = "bestMatches")
    val bestMatches: List<ResponseMatchedTicker>?
)