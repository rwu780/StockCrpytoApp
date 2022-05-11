package com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject


import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseCompanySummary(
    @Json(name = "Address")
    val address: String?,
    @Json(name = "AssetType")
    val assetType: String,
    @Json(name = "Description")
    val description: String?,
    @Json(name = "Industry")
    val industry: String?,
    @Json(name = "Name")
    val name: String?,
    @Json(name = "Sector")
    val sector: String?,
    @Json(name = "Symbol")
    val symbol: String
) {

    fun toUICompanySummary() : CompanySummary {
        return CompanySummary(
            symbol = symbol,
            name = name ?: "No available value",
            description = description ?: "No available value",
            industry = industry ?: "No available value",
            address = address ?: "No available value"
        )
    }
}