package com.example.apitest.domain.usecase

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.repository.WeatherRepository
import javax.inject.Inject
import com.example.apitest.utils.Result

class GetCitiesListAndWeatherUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<WeatherAndCitiesData> {
        return try {
            val citiesList = citiesRepository.getAllCitiesData()
            if (citiesList.isEmpty()) {
                return Result.Error(Exception("Список городов пуст"))
            }

            val weatherDataList = mutableListOf<CityWeatherData>()

            for (item in citiesList) {
                val weatherResult = weatherRepository.getWeather(item.city)
                val forecastResult = weatherRepository.getThreeHourForecast(item.city)
                val weeklyResult = weatherRepository.getWeeklyForecast(item.city)

                when {
                    weatherResult is Result.Error -> return Result.Error(
                        Exception(
                            "Ошибка при загрузке погоды для ${item.city}: +" +
                                " ${weatherResult.exception.message}")
                    )
                    forecastResult is Result.Error -> return Result.Error(
                        Exception(
                            "Ошибка ри загрузке погоды для ${item.city}: +" +
                                " ${forecastResult.exception.message}")
                    )
                    weeklyResult is Result.Error -> return Result.Error(
                        Exception(
                            "Ошибка при загрузке недельного прогноза для ${item.city}:+" +
                                "${weeklyResult.exception.message}")

                    )
                }
                if (weatherResult is Result.Success &&
                    forecastResult is Result.Success &&
                    weeklyResult is Result.Success
                ) {
                    weatherDataList.add(
                        CityWeatherData(
                            city = item.city,
                            main = weatherResult.data,
                            forecast = forecastResult.data,
                            weeklyForecast = weeklyResult.data
                        )
                    )
                }
            }
            Result.Success(
                WeatherAndCitiesData(
                    weather = weatherDataList,
                    citiesList = citiesList
                )
            )
        } catch (e: Exception) {
            Result.Error(Exception("Ошибка при загрузке данных: ${e.message}"))

        }
    }
}


data class WeatherAndCitiesData(
    val weather: List<CityWeatherData>,
    val citiesList: List<CitiesInfoTuple>
)

data class CityWeatherData(
    val city: String,
    val main: WeatherResponse,
    val forecast: List<ForecastItem>,
    val weeklyForecast: List<DailyForecast>
)