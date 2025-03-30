package com.example.apitest.domain.model

import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse

data class CityWeatherData(
    val city: String,
    val weather: WeatherResponse,
    val forecast: List<ForecastItem>,
    val weeklyForecast: List<DailyForecast>
)