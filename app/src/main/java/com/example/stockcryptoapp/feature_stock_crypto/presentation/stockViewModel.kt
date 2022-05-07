package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.stockcryptoapp.feature_stock_crypto.domain.Repository
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.FavoriateStock
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult
import com.example.stockcryptoapp.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val TAG = "stockViewModel"

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _favoriteStockList = MutableLiveData<MutableList<FavoriateStock>>(
        mutableListOf(
            FavoriateStock("AAPL", "Apple", 157.28f, 156.54f, 116120000L, 0.74f, +0.47f),
            FavoriateStock("MSFT", "Apple", 157.28f, 156.54f, 116120000L, 0.74f, +0.47f),
            FavoriateStock("FB", "Apple", 157.28f, 156.54f, 116120000L, 0.74f, +0.47f)
        )
    )
    val favoriateStock : LiveData<List<FavoriateStock>> get() = liveData<List<FavoriateStock>> { emit(_favoriteStockList.value ?: emptyList()) }

    private val _searchResult = MutableLiveData<List<MatchedResult>>()
    val searchResult: LiveData<List<MatchedResult>> get() = _searchResult

    private val _companyInfo = MutableLiveData<CompanySummary>()
    val companyInfo: LiveData<CompanySummary> get() = _companyInfo

    private var searchJob: Job? = null

    fun addStockToFavorite(ticker: String){

        _favoriteStockList.value?.add(
            FavoriateStock("FB", "Apple", 157.28f, 156.54f, 116120000L, 0.74f, +0.47f)
        )

    }

    fun searchKeyword(keyword: String){

        // Remove old jobs
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            repository.searchKeyword(keyword).collectLatest { result ->
                Log.d(TAG, "searchKeyword: ${result}")
                when(result){
                    is ResultState.SUCCESS -> {
                        result.data?.let {
                            _searchResult.postValue(it)
                        }
                    }
                    is ResultState.ERROR -> {
                        Log.d(TAG, "searchKeyword: ${result.msg}")
                    }
                }
            }
        }
    }

    fun retrieveCompanyInfo(symbol: String){

        searchJob?.cancel()
        searchJob = viewModelScope.launch {

            repository.retrieveCompanyInfo(symbol).collectLatest { result ->
                Log.d(TAG, "getCompanyOverview: ${result}")
                when(result){
                    is ResultState.SUCCESS -> {
                        result.data?.let {
                            _companyInfo.postValue(it)
                        }
                    }
                    is ResultState.LOADING -> {
                        result.data?.let {
                            _companyInfo.postValue(it)
                        }
                    }
                    is ResultState.ERROR -> {
                        Log.d(TAG, "getCompanyOverview: ${result.msg}")
                        result.data?.let {
                            _companyInfo.postValue(it)
                        }
                    }
                }
            }
        }
    }
}