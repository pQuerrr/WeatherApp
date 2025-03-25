package com.example.apitest.presentation.mainscreen.view

import WeeklyForecastTable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState

@Composable
fun MainScreenWeatherContent(state: MainScreenViewState.Success) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTemperature(weather = state.weather, city = state.city)
        ForecastList(forecast = state.forecast)
        WeeklyForecastTable(weeklyForecast = state.weeklyForecast)
    }
}