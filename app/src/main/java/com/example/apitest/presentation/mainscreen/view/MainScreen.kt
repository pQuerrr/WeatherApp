package com.example.apitest.presentation.mainscreen.view

import WeeklyForecastTable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.data.remote.response.Main
import com.example.apitest.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import kotlinx.coroutines.selects.select

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()
    val cityId = viewModel.getCityIdFromPref()
    var showFindCity by remember { mutableStateOf(cityId == null) }

    if (showFindCity) {
        LaunchedEffect(Unit) {
            viewModel.resetViewState()
            viewModel.loadCities()
        }
        FindCity(
            onCitySelected = { selectedCity ->
                showFindCity = false
                viewModel.fetchWeather(selectedCity)
            })
    } else {
        LaunchedEffect(cityId) {
            viewModel.getCityById(cityId)
        }
        Column {
            when (val state = viewState) {
                is MainScreenViewState.Success -> {
                    CityButton(cityName = state.city,
                        onClick = { showFindCity = true }
                    )
                }

                is MainScreenViewState.CitySelected -> {
                    val cityName = state.city.city
                    CityButton(cityName = cityName,
                        onClick = { showFindCity = true }
                    )
                }

                else -> {
                    CityButton(cityName = "Выберите город", onClick = { showFindCity = true })
                }
            }

            when (val state = viewState) {
                is MainScreenViewState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                }

                is MainScreenViewState.Success -> {
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

                is MainScreenViewState.CitySelected -> {
                    val cityName = state.city.city
                    LaunchedEffect(cityName) {
                        viewModel.fetchWeather(cityName)
                    }
                }

                is MainScreenViewState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.message, color = Color.Red)
                        Button(onClick = {
                            viewModel.resetViewState()
                            showFindCity = true
                        }) {
                            Text("Попробовать снова")
                        }
                    }
                }
                is MainScreenViewState.Idle -> { }
                else -> {}
            }
        }
    }
}