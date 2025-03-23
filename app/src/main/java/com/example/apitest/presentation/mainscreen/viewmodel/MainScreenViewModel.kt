package com.example.apitest.presentation.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.data.remote.response.DailyForecast
import com.example.apitest.data.remote.response.ForecastItem
import com.example.apitest.data.remote.response.WeatherResponse
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.repository.WeatherRepository
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import com.example.apitest.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//todo usecase изучить + сделать
//todo модели презентационного слоя

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesRepository,
    private val cityPreferences: CityPreferences
) : ViewModel() {
    private val _viewState = MutableStateFlow<MainScreenViewState>(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> = _viewState.asStateFlow()

    init {
        loadCities()
    }

    fun loadCities() {
        viewModelScope.launch {
            _viewState.value = MainScreenViewState.Loading
            try {
                val citiesList = citiesRepository.getAllCitiesData()
                _viewState.value = MainScreenViewState.CitiesLoaded(citiesList)
            } catch (e: Exception){
                _viewState.value = MainScreenViewState.Error("Ошибка при загрузке из БД: ${e.message}")
            }
        }
    }

    fun fetchWeather(city: String?) {
        city?.let {
            viewModelScope.launch {
                _viewState.value = MainScreenViewState.Loading
                try {
                    val weatherResult = weatherRepository.getWeather(city)
                    val forecastResult = weatherRepository.getThreeHourForecast(city)
                    val weeklyResult = weatherRepository.getWeeklyForecast(city)
                    if (weatherResult is Result.Success && forecastResult is Result.Success && weeklyResult is Result.Success) {
                        _viewState.value = MainScreenViewState.Success(
                            city = city,
                            weather = weatherResult.data,
                            forecast = forecastResult.data,
                            weeklyForecast = weeklyResult.data
                        )
                    } else {
                        val errorMessage = when {
                            weatherResult is Result.Error -> "Ошибка при загрузке погоды: ${weatherResult.exception.message}"
                            forecastResult is Result.Error -> "Ошибка при загрузке прогноза: ${forecastResult.exception.message}"
                            weeklyResult is Result.Error -> "Ошибка при загрузке недельного прогноза: ${weeklyResult.exception.message}"
                            else -> "Неизвестная ошибка"
                        }
                        _viewState.value = MainScreenViewState.Error(errorMessage)
                    }
                } catch (e: Exception) {
                    _viewState.value =
                        MainScreenViewState.Error("Ошибка получея данных: ${e.message}")
                }
            }
        }
    }

    fun resetViewState(){
        _viewState.value = MainScreenViewState.Idle
    }

    fun getCityById(cityId: Long?) {
        cityId?.let {
            viewModelScope.launch {
                try {
                    val city = citiesRepository.getCityById(cityId)
                    _viewState.value = MainScreenViewState.CitySelected(city)
                } catch (e: Exception) {
                    _viewState.value =
                        MainScreenViewState.Error("Ошибка при загрузке города ${e.message}")
                }
            }
        }
    }

    fun getCityIdFromPref(): Long? {
        return cityPreferences.getCityId()
    }
}
