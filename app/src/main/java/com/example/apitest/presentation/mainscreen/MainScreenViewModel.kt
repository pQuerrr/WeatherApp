package com.example.apitest.presentation.mainscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.DailyForecast
import com.example.apitest.data.ForecastItem
import com.example.apitest.data.HourlyWeather
import com.example.apitest.data.WeatherResponse
import com.example.apitest.data.repo.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weather = mutableStateOf<Result<WeatherResponse>?>(null)
    val weather: State<Result<WeatherResponse>?> = _weather

    private val _coordinates = mutableStateOf<Pair<BigDecimal, BigDecimal>?>(null)
    val coordinates: State<Pair<BigDecimal, BigDecimal>?> = _coordinates

    private val _hourlyWeather = mutableStateOf<Result<List<HourlyWeather>>?>(null)
    val hourlyWeather: State<Result<List<HourlyWeather>>?> = _hourlyWeather

    private val _forecast = MutableLiveData<List<ForecastItem>>()
    val forecast: LiveData<List<ForecastItem>> get() = _forecast

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast: LiveData<List<DailyForecast>> get()= _weeklyForecast

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            _weather.value = repository.getWeather(city)
        }
    }

    fun fetchThreeHourForecast(city: String) {
        viewModelScope.launch {
            val result = repository.getThreeHourForecast(city)
            result.onSuccess { forecastList ->
                _forecast.value = forecastList
            }.onFailure { exception ->
                _error.value = exception.message
            }
        }
    }

    fun fetchWeeklyForecast(city: String){
        viewModelScope.launch {
            val result = repository.getWeeklyForecast(city)
            result.onSuccess { forecast ->
                _weeklyForecast.value = forecast
            }.onFailure { exception ->
                _error.value = exception.message
            }
        }
    }



//    fun fetchHourlyWeatherByCity(city: String){
//        viewModelScope.launch {
//            val coordinatesResult = repository.getCityCoordinates(city)
//            if (coordinatesResult.isSuccess){
//                val coordinates = coordinatesResult.getOrNull()
//                if (coordinates != null){
//                    _coordinates.value = Pair(coordinates.lat,coordinates.lon)
//                    _hourlyWeather.value = repository.getHourlyWeather(
//                        coordinates.lat,
//                        coordinates.lon
//                    )
//                }
//            } else {
//                    _hourlyWeather.value= Result.failure(coordinatesResult.exceptionOrNull()!!)
//            }
//        }
//    }

}