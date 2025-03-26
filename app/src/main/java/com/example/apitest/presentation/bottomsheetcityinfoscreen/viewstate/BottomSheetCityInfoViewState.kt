package com.example.apitest.presentation.bottomsheetcityinfoscreen.viewstate

import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse

sealed class BottomSheetCityInfoViewState {
    data object Loading : BottomSheetCityInfoViewState()
    data object Idle : BottomSheetCityInfoViewState()
    data class Success(
        val city: String,
        val weather: WeatherResponse,
        val forecast: List<ForecastItem>,
        val weeklyForecast: List<DailyForecast>
    ): BottomSheetCityInfoViewState()
    data class Error(val message: String): BottomSheetCityInfoViewState()
}