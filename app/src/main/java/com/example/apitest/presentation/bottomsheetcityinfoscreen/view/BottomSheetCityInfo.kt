package com.example.apitest.presentation.bottomsheetcityinfoscreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.presentation.components.cards.ErrorCard
import com.example.apitest.presentation.components.cards.WeatherContent
import com.example.apitest.presentation.components.indicators.LoadingIndicator
import com.example.apitest.presentation.bottomsheetcityinfoscreen.viewmodel.BottomSheetCityInfoViewModel
import com.example.apitest.presentation.bottomsheetcityinfoscreen.viewstate.BottomSheetCityInfoViewState


@Composable
fun BottomSheetCityInfo(
    modifier: Modifier = Modifier,
    viewModel: BottomSheetCityInfoViewModel = hiltViewModel(),
    city: String,
    onCancelClick : () -> Unit,
    onAddClick : () -> Unit
) {

    val viewState by viewModel.viewState.collectAsState()

    LaunchedEffect(city) {
        if (city.isNotBlank()){
            viewModel.fetchWeather(city)
        }
    }

    Column (modifier = Modifier
        .padding(bottom = 100.dp)) {
        Row(
            modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { onCancelClick() }) {
                Text(text = "Отменить")
            }
            TextButton(onClick = { onAddClick() }) {
                Text(text = "Добавить")
            }
        }
        when (val state = viewState) {
            is BottomSheetCityInfoViewState.Success -> WeatherContent(state = state)
            is BottomSheetCityInfoViewState.Error -> ErrorCard(
                message = state.message,
                onRetry = {
                    viewModel.resetViewState()
                    viewModel.fetchWeather(city)
                }
            )

            is BottomSheetCityInfoViewState.Loading -> LoadingIndicator()
            else -> {}
        }
    }
}

@Preview
@Composable
private fun BottomSheetCityInfoPreview() {
    BottomSheetCityInfo(
        city = "Ялта",
        onCancelClick = {},
        onAddClick = {}
    )
}