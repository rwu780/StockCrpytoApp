package com.example.stockcryptoapp.feature_stock_crypto.data.remote

import com.example.stockcryptoapp.BuildConfig
import com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject.QuoteResponse
import com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject.ResponseCompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject.ResponseMatchedTicker
import com.example.stockcryptoapp.feature_stock_crypto.data.remote.responseObject.SearchTickerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphtAdvantageApi {

    @GET(END_POINT_QUERY)
    suspend fun searchSymbol(
        @Query("keywords") keyword: String,
        @Query("function") function: String = SEARCH_SYMBOL,
        @Query("datatype") dataType: String = DATA_TYPE,
        @Query("apikey") apikey: String = API_KEY2
    ) : SearchTickerResponse

    @GET(END_POINT_QUERY)
    suspend fun retrieveCompanyOverview(
        @Query("symbol") symbol: String,
        @Query("function") function: String = COMPANY_OVERVIEW,
        @Query("apikey") apikey: String = API_KEY
    ) : ResponseCompanySummary

    @GET(END_POINT_QUERY)
    suspend fun getQuote(
        @Query("symbol") symbol: String,
        @Query("function") function: String = RETRIEVE_QUOTE,
        @Query("apikey") apikey: String = API_KEY1
    ) : QuoteResponse

//    @GET(END_POINT_QUERY)
//    fun getActiveTicker(
//        @Query("function") function: String = LISTING_STATUS,
//        @Query("state") state: String = "active",
//        @Query("apikey") apiKey: String = API_KEY
//    )

    companion object {

        const val BASE_URL = "https://www.alphavantage.co/"

        private const val END_POINT_QUERY = "query"

        private const val SEARCH_SYMBOL = "SYMBOL_SEARCH"
        private const val RETRIEVE_QUOTE = "GLOBAL_QUOTE"
        private const val LISTING_STATUS = "LISTING_STATUS"
        private const val COMPANY_OVERVIEW = "OVERVIEW"

        private const val DATA_TYPE = "json"

        private const val API_KEY = BuildConfig.ALPHA_VANTAGE_API_KEY
        private const val API_KEY1 = BuildConfig.ALPHA_VANTAGE_API_KEY1
        private const val API_KEY2 = BuildConfig.ALPHA_VANTAGE_API_KEY2

    }
}