package com.example.apitest.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.repo.WeatherRepository
import com.example.apitest.data.response.DailyForecast
import com.example.apitest.data.response.ForecastItem
import com.example.apitest.data.response.WeatherResponse
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
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weather = MutableStateFlow<Result<WeatherResponse>?>(null)
    val weather: StateFlow<Result<WeatherResponse>?> =
        _weather.asStateFlow()//TODO Переделать на флоу, сделать тост

    private val _forecast = MutableStateFlow<Result<List<ForecastItem>>?>(null)
    val forecast: StateFlow<Result<List<ForecastItem>>?> get() = _forecast.asStateFlow() //TODO Переделать на флоу

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error.asStateFlow()//TODO Переделать на флоу, сделать тост

    private val _weeklyForecast = MutableStateFlow<Result<List<DailyForecast>>?>(null)
    val weeklyForecast: StateFlow<Result<List<DailyForecast>>?> get() = _weeklyForecast.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()


    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _weather.value = Result.Loading
            try {
                val result = repository.getWeather(city)
                _weather.value = result
            } catch (e: Exception){
                _weather.value = Result.Error(e)
                _toastMessage.value = "Ошибка при загрузке погоды: ${e.message}"
            }
        }
    }

    fun fetchThreeHourForecast(city: String) {
        viewModelScope.launch {
            _forecast.value = Result.Loading
            try {
                val result = repository.getThreeHourForecast(city)
                _forecast.value = result
            } catch (e: Exception) {
                _forecast.value = Result.Error(e)
                _toastMessage.value = "Ошибка при загрузке прогноза: ${e.message}"
            }

        }
    }

    fun fetchWeeklyForecast(city: String) {
        viewModelScope.launch {
            _weeklyForecast.value = Result.Loading
            try {
                val result = repository.getWeeklyForecast(city)
                _weeklyForecast.value = result
            } catch (e: Exception){
                _weeklyForecast.value = Result.Error(e)
                _toastMessage.value = "Ошибка при загрузке недельного прогноза: ${e.message}"
            }
        }
    }

    fun resetToast() {
        _toastMessage.value = null
    }
}
