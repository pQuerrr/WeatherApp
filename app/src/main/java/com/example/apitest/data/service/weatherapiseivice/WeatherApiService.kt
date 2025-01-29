package com.example.apitest.data.service.weatherapiseivice

import com.example.apitest.data.CityCoordinates
import com.example.apitest.data.HourlyWeatherResponse
import com.example.apitest.data.WeatherResponse
import com.example.apitest.data.ThreeHourForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>

    @GET("geo/1.0/direct")
    suspend fun getCityCoordinates(
        @Query("q") city: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): Response<List<CityCoordinates>>

    @GET("data/3.0/onecall")
    suspend fun getHourlyWeather(
        @Query("lat") lat: BigDecimal,
        @Query("lon") lon: BigDecimal,
        @Query("exclude") exclude: String = "current,minutely,daily,alerts",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): Response<HourlyWeatherResponse>

    @GET("data/2.5/forecast")
    suspend fun getThreeHourForecast(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ru",
        @Query("appid") apiKey: String
    ): Response<ThreeHourForecastResponse>
}
