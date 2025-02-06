package com.example.apitest.presentation.mainscreen.view

import WeeklyForecastTable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.presentation.mainscreen.MainScreenViewModel

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    var city by remember { mutableStateOf("Moscow") }
    val weatherState by viewModel.weather.collectAsState()
    val forecastState by viewModel.forecast.collectAsState()
    val weelkyForecastState by viewModel.weeklyForecast.collectAsState()
    val toastMessageState by viewModel.toastMessage.collectAsState()

    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(value = city, onValueChange = { city = it }, label = { Text("Введите город") })
        Button(onClick = {
            viewModel.fetchWeather(city)
            viewModel.fetchThreeHourForecast(city)
            viewModel.fetchWeeklyForecast(city)
        }) {
            Text("Получить погоду")
        }
        MainTemperature(weatherState = weatherState, city = city)
        ForecastList(forecastState = forecastState)
        WeeklyForecastTable(weeklyForecastState = weelkyForecastState)
    }
}