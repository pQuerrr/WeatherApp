package com.example.apitest.presentation.findcityscreen.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.presentation.components.cards.ErrorCard
import com.example.apitest.presentation.components.cards.SearchField
import com.example.apitest.presentation.components.indicators.LoadingIndicator
import com.example.apitest.presentation.findcityscreen.viewmodel.FindCityViewModel
import com.example.apitest.presentation.mainscreen.viewstate.MainScreenViewState
import kotlinx.coroutines.launch

@Composable
fun FindCity(
    modifier: Modifier = Modifier,
    viewModel: FindCityViewModel = hiltViewModel(),
    onCitySelected: (String) -> Unit,
) {
    val viewState by viewModel.viewState.collectAsState()
    var city by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.loadCities()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            SearchField(
                value = city ,
                onValueChange = {city = it},
                onSearch = {
                    if (city.isNotBlank()){
                        viewModel.onCitySelected(city)
                        onCitySelected(city)
                    }
                }
            )
        }
        when (val state = viewState) {
            is MainScreenViewState.CitiesLoaded -> {
                CitiesList(
                    cities = state.citiesList,
                    onDelete = { city ->
                        viewModel.onDeleteCityClick(city)
                    },
                    onSelected = {citySelected ->
                        city = citySelected.city
                        viewModel.saveCityIdToPref(citySelected.id)
                    }
                )
            }

            is MainScreenViewState.Error -> {
                ErrorCard(
                    message = state.message,
                    onRetry = {
                        viewModel.loadCities()
                    }
                )
            }
            is MainScreenViewState.Loading -> LoadingIndicator()
            else -> {}
        }
    }
}
