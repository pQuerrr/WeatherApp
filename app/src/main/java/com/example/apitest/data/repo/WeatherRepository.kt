package com.example.apitest.data.repo

import com.example.apitest.data.DailyForecast
import com.example.apitest.data.ForecastItem
import com.example.apitest.data.WeatherResponse
import com.example.apitest.data.service.weatherapiseivice.WeatherApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService,
    private val apiKey: String
) {
    suspend fun getWeather(city: String): Result<WeatherResponse> {
        return try {
            val response = apiService.getWeather(city, "metric", "ru", apiKey)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getThreeHourForecast(city: String): Result<List<ForecastItem>> {
        return try {
            val response = apiService.getThreeHourForecast(city, apiKey = apiKey)
            if (response.isSuccessful) {
                val forecastList = response.body()?.list ?: emptyList()
                Result.success(forecastList)
            } else {
                Result.failure(Exception("Error: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getWeeklyForecast(city: String):Result<List<DailyForecast>>{
        return try {
            val response = apiService.getThreeHourForecast(city,apiKey = apiKey)
            if (response.isSuccessful) {
                val forecastList = response.body()?.list ?: emptyList()

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
                Result.success(dailyForecast)
            }else{
                Result.failure(Exception("Ошибка: ${response.code()}"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

//    suspend fun getCityCoordinates(city: String): Result<CityCoordinates> {
//        return try {
//            val response = apiService.getCityCoordinates(city, apiKey = apiKey)
//            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
//                Log.d("WeatherRepository", "${response.raw()}")
//                val coordinates = response.body()!![0]
//                Log.d(
//                    "WeatherRepository",
//                    "Координаты города $city: ${coordinates.lat}, ${coordinates.lon}, ${coordinates.country}, ${coordinates.state}"
//                )
//                Result.success(coordinates)
//            } else {
//                Log.e("WeatherRepository", "Город не найден или ошибка API: ${response.code()}")
//                Result.failure(Exception("Город не найдет или другая ошибка)"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

//    suspend fun getHourlyWeather(lat: BigDecimal, lon: BigDecimal): Result<List<HourlyWeather>> {
//        return try {
//            val response = apiService.getHourlyWeather(lat, lon, apiKey = apiKey)
//            if (response.isSuccessful) {
//                Result.success(response.body()?.hourly ?: emptyList())
//            } else {
//                Result.failure(Exception("Ошибка: ${response.code()}"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
}