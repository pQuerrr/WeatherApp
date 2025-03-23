package com.example.apitest.presentation.mainscreen.viewstate

import com.example.apitest.data.local.entities.Cities
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.Weather
import com.example.apitest.data.remote.response.WeatherForecast
import com.example.apitest.data.remote.response.WeatherResponse

sealed class MainScreenViewState {
    object Loading : MainScreenViewState()
    object Idle : MainScreenViewState()
    data class Success(
        val city: String,
        val weather: WeatherResponse,
        val forecast: List<ForecastItem>,
        val weeklyForecast: List<DailyForecast>
    ): MainScreenViewState()
    data class Error(val message: String): MainScreenViewState()
    data class CitySelected(val city: CitiesInfoTuple): MainScreenViewState()
    data class CitiesLoaded(val citiesList: List<CitiesInfoTuple>): MainScreenViewState()
}
