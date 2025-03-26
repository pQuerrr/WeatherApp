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

    LaunchedEffect(Unit) {
        viewModel.getCitiesAndFetchWeatherForFirst()
    }

    if (showFindCity) {
        FindCity(
            onCitySelected = { selectedCity ->
                viewModel.fetchWeather(selectedCity)
                showFindCity = false
            })
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
                    viewModel.clearCitySelection()
                    showFindCity = true
                }
            )

            when (val state = viewState) {
                is MainScreenViewState.Loading -> {
                    LoadingIndicator()
                }
                is MainScreenViewState.Error -> ErrorCard(
                    message = state.message,
                    onRetry = {
                        viewModel.clearCitySelection()
                        viewModel.resetViewState()
                        showFindCity = true
                    }
                )
                is MainScreenViewState.Success -> {
                    if (state.citiesList.isNotEmpty()){
                        LaunchedEffect(state.citiesList.size) {
                            if (pagerState.pageCount != state.citiesList.size){
                                pagerState.scrollToPage(0)
                            }
                        }
                        LaunchedEffect(pagerState.currentPage) {
                            delay(300)
                            val currentCity = state.citiesList[pagerState.currentPage].city
                            if (currentCity != lastFetchedCity) {
                                lastFetchedCity = currentCity
                                viewModel.fetchWeather(currentCity)
                            }
                        }
                        HorizontalPager(
                            state = pagerState,
                        ) { page ->
                            val cityData = state.citiesList[page]
                            WeatherContent(
                                city = cityData.city,
                                weather = state.weather,
                                forecast = state.forecast,
                                weeklyForecast = state.weeklyForecast
                            )
                        }
                    } else showFindCity = true
                }
                else -> {}
            }
        }
    }
}