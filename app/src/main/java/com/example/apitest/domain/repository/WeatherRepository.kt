package com.example.apitest.domain.repository

import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.utils.Result

interface WeatherRepository {
    suspend fun getWeather(city: String): Result<WeatherResponse>
    suspend fun getThreeHourForecast(city: String): Result<List<ForecastItem>>
    suspend fun getWeeklyForecast(city: String): Result<List<DailyForecast>>
}