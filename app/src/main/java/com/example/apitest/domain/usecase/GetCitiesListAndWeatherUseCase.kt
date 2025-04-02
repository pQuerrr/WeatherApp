package com.example.apitest.domain.usecase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.repository.GeoRepository
import com.example.apitest.domain.repository.WeatherRepository
import javax.inject.Inject
import com.example.apitest.utils.Result
import com.google.android.gms.location.LocationAvailability
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class GetCitiesListAndWeatherUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherRepository: WeatherRepository,
    private val geoRepository: GeoRepository,
    private val context: Context
) {
    suspend operator fun invoke(): Result<WeatherAndCitiesData> {
        return try {
            val location =getCurrentLocation() ?: return Result.Error(Exception("Не удалось получить местоположение"))

            val cityResult = geoRepository.getCityName(
                lat = location.latitude.toLong(),
                lon = location.longitude.toLong(),
                limit = 1
            )

            val currentCity = when (cityResult){
                is Result.Success -> cityResult.data.name
                is Result.Error -> return Result.Error(Exception("Не удалось определить город: ${cityResult.exception.message}"))
            }

            val existingCities = citiesRepository.getAllCitiesData().toMutableList()

            existingCities.removeAll { it.city == currentCity }

            val citiesList = mutableListOf<CitiesInfoTuple>().apply {
                add(CitiesInfoTuple(city = currentCity, id = null))
                addAll(existingCities)
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

    private suspend fun getCurrentLocation(): Location? = withContext(Dispatchers.IO){
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val hasFineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

            val hasCoarseLocaton = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

            if (!hasFineLocation && !hasCoarseLocaton){
                return@withContext null
            }

            val provides = locationManager.getProviders(true)
            var bestLocation: Location? = null
            for (provider in provides){
                val location = locationManager.getLastKnownLocation(provider) ?: continue
                if (bestLocation == null || location.accuracy < bestLocation.accuracy){
                    bestLocation = location
                }
            }
            bestLocation
        } catch (e: Exception){
            null
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