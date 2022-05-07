package com.example.stockcryptoapp.util

sealed class ResultState<T>(val data: T? = null, val msg: String? = null){

    class LOADING<T>(data: T? = null) : ResultState<T>(data)
    class SUCCESS<T>(data: T?) : ResultState<T>(data)
    class ERROR<T>(data: T? = null, msg: String) : ResultState<T>(data, msg)

}
