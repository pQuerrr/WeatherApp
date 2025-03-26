package com.example.apitest.domain.usecase

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.repository.WeatherRepository
import javax.inject.Inject
import com.example.apitest.utils.Result

class GetCitiesListAndWeatherForFirstUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<WeatherAndCitiesData> {
        return try {
            val citiesList = citiesRepository.getAllCitiesData()
            if (citiesList.isNotEmpty()) {
                val weatherResult = weatherRepository.getWeather(citiesList.first().city)
                val forecastResult = weatherRepository.getThreeHourForecast(citiesList.first().city)
                val weeklyResult = weatherRepository.getWeeklyForecast(citiesList.first().city)
                if (weatherResult is Result.Success &&
                    forecastResult is Result.Success &&
                    weeklyResult is Result.Success
                ) {
                    Result.Success(
                        WeatherAndCitiesData(
                            city = citiesList.first().city,
                            weather = weatherResult.data,
                            forecast = forecastResult.data,
                            weeklyForecast = weeklyResult.data,
                            citiesList = citiesList
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
            } else {
                Result.Error(Exception("Список городов пуст"))
            }
        } catch (e: Exception) {
            Result.Error(Exception("Ошибка при загрузке данных из БД: ${e.message}"))
        }
    }
}

data class WeatherAndCitiesData(
    val city: String,
    val weather: WeatherResponse,
    val forecast: List<ForecastItem>,
    val weeklyForecast: List<DailyForecast>,
    val citiesList: List<CitiesInfoTuple>
)