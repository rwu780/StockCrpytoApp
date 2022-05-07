package com.example.stockcryptoapp.feature_stock_crypto.domain

import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult
import com.example.stockcryptoapp.util.ResultState
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun searchKeyword(keyword: String) : Flow<ResultState<List<MatchedResult>>>

    fun retrieveCompanyInfo(symbol: String) : Flow<ResultState<CompanySummary>>
}