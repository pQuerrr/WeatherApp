package com.example.apitest.presentation.mainscreen.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apitest.data.remote.response.Main
import com.example.apitest.data.remote.response.Weather
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.utils.Result

@SuppressLint("DefaultLocale")
@Composable
fun MainTemperature(
    weather: WeatherResponse,
    city: String?
) {
    city?.let {
        val weatherData = weather
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontSize = 20.sp,
                    text = city, //Город
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    fontSize = 40.sp,
                    text = "${String.format("%.0f", weatherData.main.temp)}°" //?
                )
                Text(
                    fontSize = 15.sp,
                    text = weatherData.weather[0].description.replaceFirstChar { it.uppercase() }
                )
                Text(
                    fontSize = 15.sp,
                    text = "Макс.:${String.format("%.0f", weatherData.main.tempMax)}°, " +
                            "Мин.:${String.format("%.0f", weatherData.main.tempMin)}°"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainTemperaturePreview() {
    val previewWeatherResponse = WeatherResponse(
        main = Main(
            temp = 15.0,
            tempMin = 10.0,
            tempMax = 20.0
        ),
        weather = listOf(
            Weather(
                description = "clear sky"
            )
        )
    )
    MainTemperature(weather = previewWeatherResponse, city = "Moscow")
}

