package com.example.apitest.presentation.mainscreen.viewstate

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.domain.usecase.CityWeatherData


sealed class MainScreenViewState {
    data object Loading : MainScreenViewState()
    data object Idle : MainScreenViewState()
    data class Success(
        val citiesWeatherData: List<CityWeatherData>,
        val citiesList: List<CitiesInfoTuple>
    ): MainScreenViewState()
    data class Error(val message: String): MainScreenViewState()
}

