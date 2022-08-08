/*
 * *
 *  * Created by Nethaji on 11/9/21 1:34 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 11/9/21 1:34 PM
 *
 */

package com.weather.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.di.utility.Resource
import com.weather.data.network.api.response.ResForecast
import com.weather.data.network.api.response.Temprature
import com.weather.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    private val _getTempData: MutableStateFlow<Resource<Temprature>> = MutableStateFlow(
        Resource.Loading
    )
    val getTempData: Flow<Resource<Temprature>> get() = _getTempData


    private val _getForecast: MutableStateFlow<Resource<ResForecast>> = MutableStateFlow(
        Resource.Loading
    )
    val getForecast: Flow<Resource<ResForecast>> get() = _getForecast


    fun getTemprature(city: String, APPID: String) {
        viewModelScope.launch {
            _getTempData.value = Resource.Loading
            _getTempData.value = repository.getTempratureRepo(city, APPID)
        }
    }


    fun getForecast(city: String, APPID: String) {
        viewModelScope.launch {
            _getForecast.value = Resource.Loading
            _getForecast.value = repository.getForecastRepo(city, APPID)
        }
    }


}