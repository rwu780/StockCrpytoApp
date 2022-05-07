package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.stockcryptoapp.feature_stock_crypto.domain.Repository
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.FavoriateStock
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult
import com.example.stockcryptoapp.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "stockViewModel"

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _favoriteStockList = MutableLiveData<MutableList<FavoriateStock>>(mutableListOf())
    val favoriateStock : LiveData<List<FavoriateStock>> get() = liveData<List<FavoriateStock>> { emit(_favoriteStockList.value ?: emptyList()) }

    private val _searchResult = MutableLiveData<List<MatchedResult>>()
    val searchResult: LiveData<List<MatchedResult>> get() = _searchResult

    private val _companyInfo = MutableLiveData<CompanySummary>()
    val companyInfo: LiveData<CompanySummary> get() = _companyInfo

    private var searchJob: Job? = null

    fun addFavoriateList(list: List<String>){
        for (symbol in list)
            addStockToFavorite(ticker = symbol)
    }

    fun addStockToFavorite(ticker: String){

        viewModelScope.launch {

            _favoriteStockList.value?.add(
                FavoriateStock(ticker, "Apple", 157.28f, 156.54f, 116120000L, 0.74f, +0.47f)
            )
        }
    }

    fun removeStockFromFavorite(ticker: String){
        Log.d(TAG, "removeStockFromFavorite: ${ticker}")
        Log.d(TAG, "removeStockFromFavorite: ${favoriateStock.value}")
        val item = _favoriteStockList.value?.find { it.ticker == ticker }

        Log.d(TAG, "removeStockFromFavorite: ${item}")
        _favoriteStockList.value?.remove(item)

    }

    fun isStockFavorite(symbol: String) : Boolean{

        return _favoriteStockList.value?.map { it.ticker }?.contains(symbol) ?: false

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