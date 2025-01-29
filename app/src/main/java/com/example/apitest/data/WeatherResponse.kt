package com.example.apitest.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

data class WeatherResponse(
    @SerialName("main") val main: Main,
    @SerialName("weather") val weather: List<Weather>
)

data class Main(
    @SerialName("temp") val temp: Double,
    @SerialName("temp_min") val temp_min: Double,
    @SerialName("temp_max") val temp_max: Double
)

data class Weather(
    @SerialName("description") val description: String
)

data class HourlyWeatherResponse(
    @SerialName("hourly") val hourly: List<HourlyWeather>
)

data class HourlyWeather(
    @SerialName("dt") val timestamp: Long,
    @SerialName("temp") val temperature: Double,
    @SerialName("weather") val weather: List<Weather>
)

@Serializable
data class CityCoordinates(
    @SerialName("name") val name: String,
    val lat: BigDecimal,
    val lon: BigDecimal,
    @SerialName("country") val country: String,
    @SerialName("state") val state: String
)


data class ThreeHourForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    val main: MainForecast,
    val weather: List<WeatherForecast>,
    val dt_txt: String
)

data class MainForecast(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int
)

data class WeatherForecast(
    val main: String,
    val description: String,
    val icon: String
)

data class City(
    val name: String,
    val country: String,
    val coord: Coord
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class DailyForecast(
    val date: String,
    val tempMin:Double,
    val tempMax:Double,
    val description: String
)
