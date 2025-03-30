package com.example.apitest.presentation.bottomsheetcityinfoscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.domain.usecase.GetWeatherDataUseCase
import com.example.apitest.presentation.bottomsheetcityinfoscreen.viewstate.BottomSheetCityInfoViewState
import com.example.apitest.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetCityInfoViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<BottomSheetCityInfoViewState>(
        BottomSheetCityInfoViewState.Loading)
    val viewState: StateFlow<BottomSheetCityInfoViewState> = _viewState.asStateFlow()

    fun fetchWeather(city: String?) {
        city?.let {
            viewModelScope.launch {
                _viewState.value = BottomSheetCityInfoViewState.Loading
                when (val result = getWeatherDataUseCase(city)) {
                    is Result.Success -> {
                        _viewState.value = BottomSheetCityInfoViewState.Success(
                            city = result.data.city,
                            weather = result.data.main,
                            forecast = result.data.forecast,
                            weeklyForecast = result.data.weeklyForecast
                        )
                    }
                    is Result.Error -> {
                        _viewState.value = BottomSheetCityInfoViewState.Error(
                            result.exception.message ?: "Неизвестная ошибка"
                        )
                    }
                }
            }
        }
    }
    fun resetViewState() {
        _viewState.value = BottomSheetCityInfoViewState.Idle
    }
}