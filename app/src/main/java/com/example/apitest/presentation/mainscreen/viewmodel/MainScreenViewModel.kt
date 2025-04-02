package com.example.apitest.presentation.mainscreen.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.domain.usecase.GetCitiesListAndWeatherUseCase
import com.example.apitest.domain.usecase.GetWeatherDataUseCase
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import com.example.apitest.utils.Result
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val appContext: Context
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainScreenViewState>(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> = _viewState.asStateFlow()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(appContext)
    }



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


    fun resetViewState() {
        _viewState.value = MainScreenViewState.Idle
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            appContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

}
