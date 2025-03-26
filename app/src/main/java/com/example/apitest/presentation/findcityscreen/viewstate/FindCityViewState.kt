package com.example.apitest.presentation.findcityscreen.viewstate

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse

sealed class FindCityViewState {
    data object Loading : FindCityViewState()
    data object Idle : FindCityViewState()
    data class Success(
        val citiesList: List<CitiesInfoTuple>
    ): FindCityViewState()
    data class Error(val message: String): FindCityViewState()
}