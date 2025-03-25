package com.example.apitest.presentation.mainscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.presentation.components.cards.ErrorCard
import com.example.apitest.presentation.components.indicators.LoadingIndicator
import com.example.apitest.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()
    val cityId by viewModel.cityId.collectAsState()
    var showFindCity by remember { mutableStateOf(cityId == null) }

    LaunchedEffect(cityId) {
        if (cityId == null) {
            viewModel.resetViewState()
            showFindCity = true
        } else {
            viewModel.getCityById(cityId)
        }
    }

    if (showFindCity) {
        FindCity(
            onCitySelected = { selectedCity ->
                viewModel.fetchWeather(selectedCity)
                showFindCity = false
            })
    } else {
        Column {
            CityButton(
                cityName = when (val state = viewState) {
                    is MainScreenViewState.Success -> state.city
                    is MainScreenViewState.CitySelected -> state.city.city
                    else -> "Выберите город"
                },
                onClick = {
                    viewModel.clearCitySelection()
                    showFindCity = true
                }
            )

            when (val state = viewState) {
                is MainScreenViewState.Loading -> {
                    LoadingIndicator()
                }

                is MainScreenViewState.Success -> {
                    MainScreenWeatherContent(state)
                }

                is MainScreenViewState.CitySelected -> {
                    val cityName = state.city.city
                    LaunchedEffect(cityName) {
                        viewModel.fetchWeather(cityName)
                    }
                }

                is MainScreenViewState.Error -> ErrorCard(
                    message = state.message,
                    onRetry = {
                        viewModel.clearCitySelection()
                        viewModel.resetViewState()
                        showFindCity = true
                    }
                )

                else -> {}
            }
        }
    }
}