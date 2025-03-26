package com.example.apitest.presentation.components.cards

import WeeklyForecastTable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.presentation.bottomsheetcityinfoscreen.viewstate.BottomSheetCityInfoViewState
import com.example.apitest.presentation.mainscreen.view.ForecastList
import com.example.apitest.presentation.mainscreen.view.MainTemperature
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState


@Composable
fun WeatherContent(
    city: String,
    weather: WeatherResponse,
    forecast: List<ForecastItem>,
    weeklyForecast: List<DailyForecast>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainTemperature(weather = weather, city = city)
        ForecastList(forecast = forecast)
        WeeklyForecastTable(weeklyForecast = weeklyForecast)
    }
}

@Composable
fun WeatherContent(state: MainScreenViewState.Success) {
    WeatherContent(
        city = state.city,
        weather = state.weather,
        forecast = state.forecast,
        weeklyForecast = state.weeklyForecast
    )
}

@Composable
fun WeatherContent(state: BottomSheetCityInfoViewState.Success) {
    WeatherContent(
        city = state.city,
        weather = state.weather,
        forecast = state.forecast,
        weeklyForecast = state.weeklyForecast
    )
}



