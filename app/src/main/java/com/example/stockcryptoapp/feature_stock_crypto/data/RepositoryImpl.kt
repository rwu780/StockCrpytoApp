package com.example.stockcryptoapp.feature_stock_crypto.data

import android.util.Log
import com.example.stockcryptoapp.feature_stock_crypto.data.remote.AlphtAdvantageApi
import com.example.stockcryptoapp.feature_stock_crypto.domain.Repository
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult
import com.example.stockcryptoapp.util.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "RepositoryImpl"
class RepositoryImpl @Inject constructor(
    private val alphaAdvantageApi: AlphtAdvantageApi
): Repository {

    override fun searchKeyword(keyword: String): Flow<ResultState<List<MatchedResult>>> = flow {

        val localSearchResult = emptyList<MatchedResult>()
        emit(ResultState.LOADING(localSearchResult))

        try {
            val remoteSearchResult = alphaAdvantageApi.searchSymbol(keyword)

            remoteSearchResult.bestMatches?.let { responseList ->
                emit(ResultState.SUCCESS(
                    responseList
                        .filter {
                            it.region == "United States" && it.type == "Equity"
                        }
                        .map { response ->
                        response.toMatchedResult()
                    }
                ))
            } ?: kotlin.run {
                emit(ResultState.ERROR(localSearchResult, "Empty Response"))
            }
        } catch (httpException: HttpException){
            emit(ResultState.ERROR(localSearchResult, httpException.message()))
        } catch (e: Exception) {
            emit(ResultState.ERROR(localSearchResult, e.message ?: "Unknown Error"))
        }
    }

    override fun retrieveCompanyInfo(symbol: String): Flow<ResultState<CompanySummary>> = flow {
        emit(ResultState.LOADING())
        val localResult = CompanySummary("A","B","C", "D", "E")

        emit(ResultState.LOADING(localResult))

        try{

            val remoteSearchResult = alphaAdvantageApi.retrieveCompanyOverview(symbol)
            Log.d(TAG, "retrieveCompanyInfo: ${remoteSearchResult}")
            // Update Database
            emit(ResultState.SUCCESS(
                remoteSearchResult.toUICompanySummary()
            ))

        } catch (httpException: HttpException){
            emit(ResultState.ERROR(localResult, httpException.message()))
        } catch (e: Exception) {
            emit(ResultState.ERROR(localResult, e.message ?: "Unknown Error"))
        }



    }
}