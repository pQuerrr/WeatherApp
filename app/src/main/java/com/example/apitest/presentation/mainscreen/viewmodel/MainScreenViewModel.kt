package com.example.apitest.presentation.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.domain.usecase.GetCitiesListAndWeatherUseCase
import com.example.apitest.domain.usecase.GetWeatherDataUseCase
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import com.example.apitest.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val cityPreferences: CityPreferences,
    private val getCitiesListAndWeatherUseCase: GetCitiesListAndWeatherUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainScreenViewState>(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> = _viewState.asStateFlow()

    private val _cityId = MutableStateFlow(cityPreferences.getCityId())
    val cityId: StateFlow<Long?> = _cityId.asStateFlow()

    fun getCitiesAndFetchWeather() {
        viewModelScope.launch {
            _viewState.value = MainScreenViewState.Loading
            when (val result = getCitiesListAndWeatherUseCase()) {
                is Result.Success -> {
                    _viewState.value = MainScreenViewState.Success(
                        citiesWeatherData = result.data.weather,
                        citiesList = result.data.citiesList
                    )
                }

                is Result.Error -> {
                    _viewState.value = MainScreenViewState.Error(
                        result.exception.message ?: "Неизвестная ошибка"
                    )
                }
            }
        }
    }

//    fun fetchWeather(city: String) {
//        viewModelScope.launch {
//            val currentState = _viewState.value
//            val currentCities =
//                (currentState as? MainScreenViewState.Success)?.citiesList ?: emptyList()
//            _viewState.value = MainScreenViewState.Loading
//            when (val result = getWeatherDataUseCase(city)) {
//                is Result.Success -> {
//                _viewState.value = MainScreenViewState.Success(
//                    city = result.data.city,
//                    weather = result.data.weather,
//                    forecast = result.data.forecast,
//                    weeklyForecast = result.data.weeklyForecast,
//                    citiesList = currentCities
//                )
//            }
//                is Result.Error -> {
//                    _viewState.value = MainScreenViewState.Error(
//                        result.exception.message ?: "Ошибка загрузки погоды"
//                    )
//            }
//            }
//        }
//    }

    fun resetViewState() {
        _viewState.value = MainScreenViewState.Idle
    }

}
