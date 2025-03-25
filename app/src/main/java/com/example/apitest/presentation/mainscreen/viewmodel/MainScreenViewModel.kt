package com.example.apitest.presentation.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.repository.WeatherRepository
import com.example.apitest.domain.usecase.GetWeatherDataUseCase
import com.example.apitest.domain.usecase.LoadCitiesListUseCase
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
    private val citiesRepository: CitiesRepository,
    private val cityPreferences: CityPreferences,
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainScreenViewState>(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> = _viewState.asStateFlow()

    private val _cityId = MutableStateFlow(cityPreferences.getCityId())
    val cityId: StateFlow<Long?> = _cityId.asStateFlow()

    fun fetchWeather(city: String?) {
        city?.let {
            viewModelScope.launch {
                _viewState.value = MainScreenViewState.Loading
                when (val result = getWeatherDataUseCase(city)) {
                    is Result.Success -> {
                        _viewState.value = MainScreenViewState.Success(
                            city = result.data.city,
                            weather = result.data.weather,
                            forecast = result.data.forecast,
                            weeklyForecast = result.data.weeklyForecast
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
    }


    fun resetViewState() {
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

    fun deleteIdFromPref() {
        cityPreferences.clearCityId()
    }

    fun clearCitySelection(){
        viewModelScope.launch {
            cityPreferences.clearCityId()
            _cityId.value = null
        }
    }
}
