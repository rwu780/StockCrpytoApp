package com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject


import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseMatchedTicker(
    @Json(name = "8. currency")
    val currency: String,
    @Json(name = "6. marketClose")
    val marketClose: String,
    @Json(name = "5. marketOpen")
    val marketOpen: String,
    @Json(name = "9. matchScore")
    val matchScore: String,
    @Json(name = "2. name")
    val name: String,
    @Json(name = "4. region")
    val region: String,
    @Json(name = "1. symbol")
    val symbol: String,
    @Json(name = "7. timezone")
    val timezone: String,
    @Json(name = "3. type")
    val type: String
) {

    fun toMatchedResult() : MatchedResult {
        return MatchedResult(
            ticker = symbol,
            name = name,
            matchScore = matchScore
        )
    }
}