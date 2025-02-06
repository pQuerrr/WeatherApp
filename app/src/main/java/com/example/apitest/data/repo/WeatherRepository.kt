package com.example.apitest.data.repo

import android.util.Log
import com.example.apitest.data.response.DailyForecast
import com.example.apitest.data.response.ForecastItem
import com.example.apitest.data.response.WeatherResponse
import com.example.apitest.data.service.weatherapiseivice.WeatherApiService
import javax.inject.Inject
import com.example.apitest.utils.Result

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService,
    private val apiKey: String
) {
    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, "metric", "ru", apiKey)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getThreeHourForecast(city: String): Result<List<ForecastItem>> {
        return try {
            val response = apiService.getThreeHourForecast(city, apiKey = apiKey)
            if (response.isSuccessful) {
                val forecastList = response.body()?.forecastItem ?: emptyList()
                Log.d("WeeklyForecast", "ForecastList from API: $forecastList")

                Result.Success(forecastList)
            } else {
                Result.Error(Exception("Error: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getWeeklyForecast(city: String):Result<List<DailyForecast>>{
        return try {
            val response = apiService.getThreeHourForecast(city,apiKey = apiKey)
            if (response.isSuccessful) {
                val forecastList = response.body()?.forecastItem ?: emptyList()

                val dailyForecast = forecastList.groupBy {
                    it.dt_txt.substringBefore(" ")
                }.map { (date,forecasts) ->
                    val tempMin = forecasts.minOf {it.main.temp_min}
                    val tempMax = forecasts.maxOf {it.main.temp_max}
                    val description = forecasts
                        .flatMap { it.weather }
                        .groupingBy { it.description }
                        .eachCount()
                        .maxByOrNull { it.value }?.key ?:"No data"
                    DailyForecast(date,tempMin,tempMax,description)
                }
                Result.Success(dailyForecast)
            }else{
                Result.Error(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception){
            Result.Error(e)
        }
    }
}