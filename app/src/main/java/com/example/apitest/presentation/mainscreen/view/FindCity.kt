package com.example.apitest.presentation.mainscreen.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.presentation.components.cards.ErrorCard
import com.example.apitest.presentation.mainscreen.viewmodel.FindCityViewModel
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
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = {
                    Text(
                        text = "Введите название города"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier
                            .padding(0.dp),
                        onClick = {
                            if (city.isNotBlank()) {
                                viewModel.onCitySelected(city)
                                onCitySelected(city)
                            }
                        },
                    ) {

                        Icon(
                            Icons.Filled.Search,
                            contentDescription = " Поиск",
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            )
        }
        when (val state = viewState) {
            is MainScreenViewState.CitiesLoaded -> {
                LazyColumn {
                    items(state.citiesList) { item ->
                        FindCityItem(
                            onDeleteCityButtonClick = {
                                coroutineScope.launch {
                                    viewModel.deleteFromDB(item)
                                    viewModel.loadCities()
                                }
                            },
                            onChooseCityButtonClick = {
                                city = item.city
                                viewModel.saveCityIdToPref(item.id)
                            },
                            cityItem = item
                        )
                    }
                }
            }

            is MainScreenViewState.Error -> {
                ErrorCard(
                    message = state.message,
                    onRetry = { viewModel.loadCities() }
                )
            }

            else -> {}
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun FindCityPreview() {
    FindCity(onCitySelected = {})
}

val previewCitiesList = listOf(
    CitiesInfoTuple(
        city = "Moscow",
        id = 1
    ),
    CitiesInfoTuple(
        city = "Yalta",
        id = 2
    )
)