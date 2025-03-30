package com.example.apitest.presentation.findcityscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.domain.usecase.ChooseCityUseCase
import com.example.apitest.domain.usecase.DeleteFromDBUseCase
import com.example.apitest.domain.usecase.GetCitiesListUseCase
import com.example.apitest.presentation.findcityscreen.viewstate.FindCityViewState
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
    private val getCitiesListUseCase: GetCitiesListUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<FindCityViewState>(FindCityViewState.Loading)
    val viewState: StateFlow<FindCityViewState> = _viewState.asStateFlow()
    fun saveCityIdToPref(cityId: Long) {
        cityPreferences.saveCityId(cityId)
    }

    fun onCitySelected(cityName: String) {
        viewModelScope.launch {
            chooseCityUseCase(cityName)
        }
    }


    fun loadCities() {
        viewModelScope.launch {
            getCitiesList()
        }
    }

    fun onDeleteCityClick(city: CitiesInfoTuple) {
        viewModelScope.launch {
            deleteFromDB(city)
            loadCities()
        }
    }

    private suspend fun deleteFromDB(city: CitiesInfoTuple) {
        deleteFromDBUseCase(city)
    }

    private suspend fun getCitiesList() {
        _viewState.value = FindCityViewState.Loading
        when (val result = getCitiesListUseCase()) {
            is Result.Success -> {
                _viewState.value = FindCityViewState.Success(result.data)
            }
            is Result.Error -> {
                _viewState.value =
                    FindCityViewState.Error(result.exception.message ?: "Не известная ошибка")
            }
        }
    }
}