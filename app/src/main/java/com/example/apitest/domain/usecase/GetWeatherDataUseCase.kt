package com.example.apitest.domain.usecase

import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.ThreeHourForecastResponse
import com.example.apitest.data.remote.response.Weather
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.domain.repository.WeatherRepository
import com.example.apitest.utils.Result
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(city: String): Result<WeatherData> {
        return try {
            val weatherResult = weatherRepository.getWeather(city)
            val forecastResult = weatherRepository.getThreeHourForecast(city)
            val weeklyResult = weatherRepository.getWeeklyForecast(city)

            if (weatherResult is Result.Success &&
                forecastResult is Result.Success &&
                weeklyResult is Result.Success) {
                Result.Success(
                    WeatherData(
                        city = city,
                        weather = weatherResult.data,
                        forecast = forecastResult.data,
                        weeklyForecast = weeklyResult.data
                    )
                )
            } else {
                val errorMessage = when {
                    weatherResult is Result.Error -> "Ошибка при загрузке погоды: ${weatherResult.exception.message}"
                    forecastResult is Result.Error -> "Ошибка при загрузке прогноза: ${forecastResult.exception.message}"
                    weeklyResult is Result.Error -> "Ошибка при загрузке недельного прогноза: ${weeklyResult.exception.message}"
                    else -> "Неизвестная ошибка"
                }
                Result.Error(Exception(errorMessage))
            }
        } catch (e:Exception) {
            Result.Error(Exception("Ошибка получения данных"))
        }
    }
}

data class WeatherData(
    val city: String,
    val weather: WeatherResponse,
    val forecast: List<ForecastItem>,
    val weeklyForecast: List<DailyForecast>
)