package com.weather.data.repository

import com.weather.di.utility.ResponseReceiver
import com.weather.data.network.api.service.ServiceApi
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ServiceApi) : ResponseReceiver {


    suspend fun getTempratureRepo(city: String, APPID: String) =
        callApi { api.getTemprature(city, APPID) }

    suspend fun getForecastRepo(city: String, APPID: String) =
        callApi { api.getForecast(city, APPID) }


    companion object Factory {
        fun create(api: ServiceApi) = MainRepository(api)
    }
}