package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.stockcryptoapp.feature_stock_crypto.domain.Repository
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.Stock
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult
import com.example.stockcryptoapp.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "stockViewModel"

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _favoriteStockList = MutableLiveData<MutableList<Stock>>()
    val stock: LiveData<MutableList<Stock>> = _favoriteStockList
//        get() = liveData {
//            emit(
//                _favoriteStockList.value ?: emptyList()
//            )
//        }

    private val _currentStock = MutableLiveData<Stock>()
    val currentStock: LiveData<Stock> get() = _currentStock

    private val _searchResult = MutableLiveData<List<MatchedResult>>()
    val searchResult: LiveData<List<MatchedResult>> get() = _searchResult

    private val _companyInfo = MutableLiveData<CompanySummary>()
    val companyInfo: LiveData<CompanySummary> get() = _companyInfo


    private var searchJob: Job? = null

    fun addFavoriateList(list: List<String>, errorMsg: () -> Unit) {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {

                val jobs = list.map {
                    Log.d(TAG, "addFavoriateList: Running Async ${it}")
                    async { repository.retrieveQuoteInfo(symbol = it) }
                }.awaitAll()

                Log.d(TAG, "addFavoriateList: Finish")
                val stockList = mutableListOf<Stock>()
                for (job in jobs) {
                    job.collectLatest { result ->
                        Log.d(TAG, "addFavoriateList: ${job} ${result.data}")
                        when (result) {
                            is ResultState.SUCCESS -> {
                                result.data?.let {
                                    stockList.add(it)
                                }

                            }
                        }
                    }
                }
                _favoriteStockList.postValue(stockList)
            }
        }
    }

    fun addStockToFavorite(ticker: String, errorMsg: () -> Unit) {

        if (!isStockFavorite(ticker)) {
            viewModelScope.launch {
                repository.retrieveQuoteInfo(ticker).collect { result ->
                    when (result) {
                        is ResultState.SUCCESS -> {
                            result.data?.let {
                                Log.d(TAG, "addStockToFavorite: ${it}")
                                _favoriteStockList.value?.add(it)
                            }
                        }
                        is ResultState.ERROR -> {
                            result.data?.let {
                                _favoriteStockList.value?.add(it)
                            } ?: run{ errorMsg() }
                        }
                        else -> {
                            Log.d(TAG, "addStockToFavorite: ${result.msg}")

                        }
                    }
                }
            }
        }
    }

    fun removeStockFromFavorite(ticker: String) {

        val item = _favoriteStockList.value?.find { it.ticker == ticker }

        _favoriteStockList.value?.remove(item)

    }

    fun isStockFavorite(symbol: String): Boolean {

        return _favoriteStockList.value?.map { it.ticker }?.contains(symbol) ?: false

    }

    fun searchKeyword(keyword: String) {

        // Remove old jobs
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            repository.searchKeyword(keyword).collectLatest { result ->
                when (result) {
                    is ResultState.SUCCESS -> {
                        result.data?.let {
                            _searchResult.postValue(it)
                        }
                    }
                    is ResultState.LOADING -> {
                        Log.d(TAG, "searchKeyword: ${result.msg}")
                    }
                    is ResultState.ERROR -> {
                        Log.d(TAG, "searchKeyword: ${result.msg}")
                    }
                }
            }
        }
    }

    fun retrieveSymbolInfo(symbol: String, errorMsg: () -> Unit) {

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            retrieveStockInfo(symbol, errorMsg)
            retrieveCompanyInfo(symbol)
        }


    }

    suspend fun retrieveStockInfo(symbol: String, errorMsg: () -> Unit) {

        repository.retrieveQuoteInfo(symbol).collectLatest { result ->
            when (result) {
                is ResultState.SUCCESS -> {
                    result.data?.let {
                        _currentStock.postValue(it)
                    }
                }
                is ResultState.ERROR -> {
                    result.data?.let {
                        _currentStock.postValue(it)
                    } ?: kotlin.run { errorMsg() }
                }
            }
        }


    }

    suspend fun retrieveCompanyInfo(symbol: String) {


        repository.retrieveCompanyInfo(symbol).collectLatest { result ->
            when (result) {
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