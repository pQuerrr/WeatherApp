package com.example.apitest.presentation.mainscreen.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.MainForecast
import com.example.apitest.data.remote.response.WeatherForecast

@SuppressLint("DefaultLocale")
@Composable
fun ForecastItemCard(
    forecastItem: ForecastItem,
    time: String,
    date: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = date,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = time,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                val iconUrl = getIconUrl(forecastItem.weather.firstOrNull()?.icon!!)
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(40.dp)
                )

                Text(
                    text = "${String.format("%.0f",forecastItem.main.temp)}°C",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

fun getIconUrl(iconId: String) = "https://openweathermap.org/img/wn/$iconId@2x.png"

@Preview
@Composable
private fun ForecastItemCardPreview() {
    ForecastItemCard(
        forecastItem = ForecastItem(
            dt = 10000,
            dt_txt = "22.10.2001",
            main = MainForecast(
                temp_max = 2.0,
                temp_min = 2.0,
                temp = 2.0,
                feels_like = 2.0,
                pressure = 2,
                humidity = 2),
            weather = listOf(WeatherForecast(
                main = "asd",
                description = "asd",
                icon = "asd"))
        ),
        time = "10.10.2001" ,
        date = ""
    )
}