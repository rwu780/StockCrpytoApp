package com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject


import com.example.stockcryptoapp.feature_stock_crypto.domain.model.Stock
import com.squareup.moshi.Json

data class GlobalQuote(
    @Json(name = "09. change")
    val change: String,
    @Json(name = "10. change percent")
    val changePercent: String,
    @Json(name = "03. high")
    val high: String,
    @Json(name = "07. latest trading day")
    val latestTradingDay: String,
    @Json(name = "04. low")
    val low: String,
    @Json(name = "02. open")
    val `open`: String,
    @Json(name = "08. previous close")
    val previousClose: String,
    @Json(name = "05. price")
    val price: String,
    @Json(name = "01. symbol")
    val symbol: String,
    @Json(name = "06. volume")
    val volume: String
) {

    fun toStock(name: String) : Stock {
        return Stock(
            ticker = symbol,
            name = name,
            currentPrice = price,
            lastClosePrice = previousClose,
            volume = volume,
            change = change,
            changePercent = changePercent,
        )
    }
}
