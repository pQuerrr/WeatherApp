package com.example.apitest.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    @SerialName("main") val main: Main,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("name") val cityName: String? = null
)

@Serializable
data class Main(
    @SerialName("temp") val temp: Double,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double,
    @SerialName("feels_like") val feelsLike: Double? = null,
    @SerialName("pressure") val pressure: Int? = null,
    @SerialName("humidity") val humidity: Int? = null
)

@Serializable
data class Weather(
    @SerialName("description") val description: String,
    @SerialName("icon") val icon: String? = null
)