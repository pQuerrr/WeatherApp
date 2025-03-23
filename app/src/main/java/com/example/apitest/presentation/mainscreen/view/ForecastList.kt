package com.example.apitest.presentation.mainscreen.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.MainForecast
import com.example.apitest.data.remote.response.WeatherForecast
import java.text.SimpleDateFormat
import java.util.Date
import com.example.apitest.utils.Result

@SuppressLint("SimpleDateFormat")
@Composable
fun ForecastList(
    forecast: List<ForecastItem>
) {
    val dateTimeFormat = SimpleDateFormat("dd.MM") // это пизда
    val timeDataFormat = SimpleDateFormat("HH:mm") // это пизда
    LazyRow() {
        items(forecast) { forecastItem ->
            ForecastItemCard(
                forecastItem = forecastItem,
                time = timeDataFormat.format(Date(forecastItem.dt * 1000)),
                date = dateTimeFormat.format(Date(forecastItem.dt * 1000))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastListPreview_Success() {
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
                temp_min = -6.0,
                temp_max = -4.0,
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
    ForecastList(forecast = testForecastList)
}
