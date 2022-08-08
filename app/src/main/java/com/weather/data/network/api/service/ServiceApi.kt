package com.weather.data.network.api.service

import com.weather.data.network.api.response.ResForecast
import com.weather.data.network.api.response.Temprature
import com.weather.di.utility.API.FORECAST
import com.weather.di.utility.API.WEATHER
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {

    @GET(WEATHER)
    suspend fun getTemprature(@Query("q") city: String, @Query("APPID") APPID: String): Temprature

    @GET(FORECAST)
    suspend fun getForecast(@Query("q") city: String, @Query("APPID") APPID: String): ResForecast

}