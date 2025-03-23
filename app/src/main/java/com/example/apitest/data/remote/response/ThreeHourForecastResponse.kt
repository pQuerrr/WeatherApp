package com.example.apitest.data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreeHourForecastResponse(
    @SerialName("list") val forecastItem: List<ForecastItem>,
    @SerialName("city") val city: City
)

@Serializable
data class ForecastItem(
    @SerialName("dt") val dt: Long,
    @SerialName("main") val main: MainForecast,
    @SerialName("weather") val weather: List<WeatherForecast>,
    @SerialName("dt_txt") val dt_txt: String
)

@Serializable
data class MainForecast(
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feels_like: Double,
    @SerialName("temp_min") val temp_min: Double,
    @SerialName("temp_max") val temp_max: Double,
    @SerialName("pressure") val pressure: Int,
    @SerialName("humidity") val humidity: Int
)

@Serializable
data class WeatherForecast(
    @SerialName("main") val main: String,
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String
)

@Serializable
data class City(
    @SerialName("name") val city: String,
    @SerialName("country") val country: String,
    @SerialName("coord") val coordinates: Coordinates
)

@Serializable
data class Coordinates(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double
)