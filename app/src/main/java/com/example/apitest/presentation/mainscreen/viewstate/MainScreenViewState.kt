package com.example.apitest.presentation.mainscreen.viewstate

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse

sealed class MainScreenViewState {
    data object Loading : MainScreenViewState()
    data object Idle : MainScreenViewState()
    data class Success(
        val city: String,
        val weather: WeatherResponse,
        val forecast: List<ForecastItem>,
        val weeklyForecast: List<DailyForecast>,
        val citiesList: List<CitiesInfoTuple>
    ): MainScreenViewState()
    data class Error(val message: String): MainScreenViewState()
}
