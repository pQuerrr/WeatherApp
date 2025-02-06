package com.example.apitest.data.service.weatherapiseivice

import com.example.apitest.data.response.ThreeHourForecastResponse
import com.example.apitest.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

    @GET("data/2.5/forecast")
    suspend fun getThreeHourForecast(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") apiKey: String
    ): Response<ThreeHourForecastResponse>
}
