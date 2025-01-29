package com.example.apitest.presentation.mainscreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.apitest.data.DailyForecast
import com.example.apitest.data.ForecastItem
import com.example.apitest.data.testdata.DailyForecastTestData
import com.example.apitest.data.testdata.testForecastList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val weatherState = viewModel.weather.value
//    val hourlyWeatherState = viewModel.hourlyWeather.value
//    val coordinates = viewModel.coordinates.value
//    val forecast by viewModel.forecast.observeAsState()
//    val error by viewModel.error.observeAsState()
    var city by remember { mutableStateOf("Moscow") }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        TextField(value = city, onValueChange = { city = it }, label = { Text("Введите город") })
        Button(onClick = {
            viewModel.fetchWeather(city)
            viewModel.fetchThreeHourForecast(city)
            viewModel.fetchWeeklyForecast(city)
        }) {
            Text("Получить погоду")
        }
        weatherState?.fold(onSuccess = {
            val weather = weatherState.getOrNull()
            val forecast by viewModel.forecast.observeAsState()
            val weeklyForecast by viewModel.weeklyForecast.observeAsState(emptyList())
//            val error by viewModel.error.observeAsState()

            if (weather != null) {
                MainTemperature(city = city, temp = weather.main.temp, description = weather.weather[0].description, min = weather.main.temp_min, max = weather.main.temp_max )
//                Text("Температура: ${weather.main.temp}°C")
//                Text("Погода: ${
//                    weather.weather[0].description.replaceFirstChar {
//                        if (it.isLowerCase()) it.titlecase(
//                            Locale.getDefault()
//                        ) else it.toString()
//                    }
//                }")
            } else {
                Text("Введите город и нажмите 'Получить погоду'")
            }
            if (forecast != null) {
                forecast?.let { forecastItems ->
                    ForecastList(forecastItems = forecastItems)
                } ?: run {
                    // Показать индикатор загрузки, пока данные загружаются
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                WeeklyForecastTable(forecast = weeklyForecast)
            }
        }, onFailure = {
            Text("Ошибка: ${weatherState.exceptionOrNull()?.message}")
        }


        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ForecastItemCard(forecastItem: ForecastItem, time: String, date: String) {
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
                    text = date, // Время прогноза
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = time, // Время прогноза
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
//                Text(
//                    text = forecastItem.weather.firstOrNull()?.description?.replaceFirstChar {
//                        if (it.isLowerCase()) it.titlecase(
//                            Locale.getDefault()
//                        ) else it.toString()
//                    } ?: "Нет данных", // Описание погоды":
//                )

                // Иконка погоды
                val iconUrl =
                    "https://openweathermap.org/img/wn/${forecastItem.weather.firstOrNull()?.icon}@2x.png"
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(40.dp)
                )
                // Температура
                Text(
                    text = "${String.format("%.0f", forecastItem.main.temp)}°C", // Температура
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun ForecastList(
    forecastItems: List<ForecastItem>
) {
    val dtf = SimpleDateFormat("dd.MM")
    val tdf = SimpleDateFormat("HH:mm")
    LazyRow(
        modifier = Modifier.fillMaxSize()
    ) {
        items(forecastItems) { forecastItem ->

            ForecastItemCard(
                forecastItem = forecastItem,
                time = tdf.format(Date(forecastItem.dt * 1000)),
                date = dtf.format(Date(forecastItem.dt * 1000))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastListPreview() {
    ForecastList(forecastItems = testForecastList)
}

@Composable
fun MainTemperature(
    modifier: Modifier = Modifier,
    city: String,
    temp: Double,
    description: String,
    min: Double,max: Double
) {
    Card {
        Column(modifier = Modifier
            .fillMaxWidth()) {
            Text(
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = city //Город
            )
            Text(
                fontSize = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "${String.format("%.0f", temp)}°"
            )
            Text(
                fontSize = 15.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = description.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            )
            Text(
                fontSize = 15.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Макс.:${String.format("%.0f", max)}°,Мин.:${String.format("%.0f", min)}°"
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun MainTemperaturePreview() {
    MainTemperature(city = "Ялта", temp = 10.0, description = "временами облачно", min = 10.0, max = 10.0)
}

@Composable
fun WeeklyForecastTable(
    modifier: Modifier = Modifier,
    forecast: List<DailyForecast>) {
    LazyColumn(modifier = modifier
        .padding(16.dp)
        .fillMaxSize()) {
        Log.d("UI", "Forecast size: ${forecast.size}")
        items(forecast) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Column {
                            Text(text = item.date, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = item.description,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Min: ${item.tempMin}°C",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Max: ${item.tempMax}°C",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun WeeklyForecastTablePreview() {
    WeeklyForecastTable(modifier = Modifier,DailyForecastTestData)
}
//@Composable
//fun HourlyWeatherItem(weather: HourlyWeather) {
//    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//    val time = sdf.format(Date(weather.timestamp * 1000)) // Конвертация времени из Unix
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = time, modifier = Modifier.weight(1f))
//        Text(text = "${weather.temperature}°C", modifier = Modifier.weight(1f))
//        Text(
//            text = weather.weather.firstOrNull()?.description ?: "N/A",
//            modifier = Modifier.weight(2f),
//            textAlign = TextAlign.End
//        )
//    }
//}

//@Preview
//@Composable
//private fun PreviewHourlyWeatherItem() {
//    HourlyWeatherItem()
//}