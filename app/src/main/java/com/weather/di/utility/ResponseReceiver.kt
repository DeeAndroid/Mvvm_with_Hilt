package com.weather.di.utility

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface ResponseReceiver {
    suspend fun <T> callApi(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.message().toString())
                    }
                    else -> Resource.Failure(true, null, throwable.message.toString())
                }
            }
        }
    }
}