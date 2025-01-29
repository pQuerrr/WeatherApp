package com.example.apitest.data.testdata

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.data.DailyForecast
import com.example.apitest.data.ForecastItem
import com.example.apitest.data.MainForecast
import com.example.apitest.data.WeatherForecast
import com.example.apitest.presentation.mainscreen.ForecastItemCard

val testForecastItem = ForecastItem(
    dt = 1674120000L, // Пример времени в формате Unix
    main = MainForecast(
        temp = 20.5,
        feels_like = 18.0,
        temp_min = 19.0,
        temp_max = 21.0,
        pressure = 1012,
        humidity = 60
    ),
    weather = listOf(
        WeatherForecast(
            main = "Дождь",
            description = "небольшой дождь",
            icon = "10d" // Код иконки OpenWeather
        )
    ),
    dt_txt = "2025-01-19 15:00:00"
)

val testForecastList = listOf(
    ForecastItem(
        dt = 1674120000L,
        main = MainForecast(
            temp = 20.5,
            feels_like = 18.0,
            temp_max = 19.0,
            temp_min = 21.0,
            pressure = 1012,
            humidity = 60
        ),
        weather = listOf(
            WeatherForecast(
                main = "Дождь",
                description = "небольшой дождь",
                icon = "10d"
            )
        ),
        dt_txt = "2025-01-19 15:00:00"
    ),
    ForecastItem(
        dt = 1674120000L,
        main = MainForecast(
            temp = -5.0,
            feels_like = -10.0,
            temp_max = -6.0,
            temp_min = -4.0,
            pressure = 1020,
            humidity = 80
        ),
        weather = listOf(
            WeatherForecast(
                main = "Снег",
                description = "снегопад",
                icon = "13d"
            )
        ),
        dt_txt = "2025-01-19 18:00:00"
    )
)

val DailyForecastTestData = listOf(
    DailyForecast("2025-01-25", -5.0, 3.0, "Clear sky"),
    DailyForecast("2025-01-26", -7.0, 2.0, "Snow"),
    DailyForecast("2025-01-27", -10.0, 0.0, "Cloudy")
)

