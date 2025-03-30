package com.example.apitest.presentation.mainscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.presentation.components.cards.ErrorCard
import com.example.apitest.presentation.components.cards.WeatherContent
import com.example.apitest.presentation.components.indicators.LoadingIndicator
import com.example.apitest.presentation.findcityscreen.view.FindCity
import com.example.apitest.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import kotlinx.coroutines.delay

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()
    var showFindCity by remember { mutableStateOf(false) }
    var lastFetchedCity by remember { mutableStateOf<String?>(null) }
    val pagerState = rememberPagerState {
        (viewState as? MainScreenViewState.Success)?.citiesList?.size ?: 0
    }

    LaunchedEffect(showFindCity) {
        if (!showFindCity){
        viewModel.getCitiesAndFetchWeather()
        }
    }


    if (showFindCity) {
        FindCity(
            onCitySelected = { selectedCity ->
                viewModel.getCitiesAndFetchWeather()
                showFindCity = false
            },
            onBackClick = {showFindCity = false})
    } else {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        )
            {
            CityButton(
                cityName = "Найти город",
                onClick = {
                    showFindCity = true
                }
            )

            when (val state = viewState) {
                is MainScreenViewState.Loading -> {
                    LoadingIndicator()
                }
                is MainScreenViewState.Error -> {
                    val isEmptyListError = state.message.contains("Список городов пуст")
                    if (isEmptyListError) {
                        LaunchedEffect(isEmptyListError) {
                            viewModel.resetViewState()
                            showFindCity = isEmptyListError
                        }
                    } else {
                        ErrorCard(
                            message = state.message,
                            onRetry = {
                                showFindCity = true
                                viewModel.resetViewState()
                            }
                        )
                    }
                }
                is MainScreenViewState.Success -> {
                    when {
                        state.citiesWeatherData.isEmpty() -> {
                            LaunchedEffect (Unit) {
                                showFindCity = true
                            }
                        }
                    }
                    if (state.citiesList.isNotEmpty()){
                        LaunchedEffect(state.citiesList.size) {
                            if (pagerState.pageCount != state.citiesList.size){
                                pagerState.scrollToPage(0)
                            }
                        }
                        HorizontalPager(state = pagerState) { page ->
                            val cityData = state.citiesWeatherData[page]
                            WeatherContent(
                                city = cityData.city,
                                weather = state.citiesWeatherData[page].main,
                                forecast = state.citiesWeatherData[page].forecast,
                                weeklyForecast = state.citiesWeatherData[page].weeklyForecast
                            )
                        }
                    } else showFindCity = true
                }
                else -> {}
            }
        }
    }
}