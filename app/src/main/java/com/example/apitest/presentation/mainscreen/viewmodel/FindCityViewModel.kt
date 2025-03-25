package com.example.apitest.presentation.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.usecase.ChooseCityUseCase
import com.example.apitest.domain.usecase.DeleteFromDBUseCase
import com.example.apitest.domain.usecase.LoadCitiesListUseCase
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import com.example.apitest.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindCityViewModel @Inject constructor(
    private val cityPreferences: CityPreferences,
    private val chooseCityUseCase: ChooseCityUseCase,
    private val deleteFromDBUseCase: DeleteFromDBUseCase,
    private val loadCitiesListUseCase: LoadCitiesListUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<MainScreenViewState>(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> = _viewState.asStateFlow()

    fun saveCityIdToPref(cityId: Long) {
        cityPreferences.saveCityId(cityId)
    }

    fun onCitySelected(cityName: String) {
        viewModelScope.launch {
            chooseCityUseCase(cityName)
        }
    }

    fun deleteFromDB(city: CitiesInfoTuple) {
        viewModelScope.launch {
            deleteFromDBUseCase(city)
        }
    }

    fun loadCities() {
        viewModelScope.launch {
            _viewState.value = MainScreenViewState.Loading
            when (val result = loadCitiesListUseCase()) {
                is Result.Success -> {
                    _viewState.value = MainScreenViewState.CitiesLoaded(result.data)
                }
                is Result.Error -> {
                    _viewState.value =
                        MainScreenViewState.Error(result.exception.message ?: "Не известная ошибка")
                }
            }
        }
    }
}