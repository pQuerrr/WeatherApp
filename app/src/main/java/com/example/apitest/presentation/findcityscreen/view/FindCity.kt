package com.example.apitest.presentation.findcityscreen.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.apitest.presentation.bottomsheetcityinfoscreen.view.BottomSheetCityInfo
import com.example.apitest.presentation.components.cards.ErrorCard
import com.example.apitest.presentation.components.cards.SearchField
import com.example.apitest.presentation.components.indicators.LoadingIndicator
import com.example.apitest.presentation.findcityscreen.viewmodel.FindCityViewModel
import com.example.apitest.presentation.findcityscreen.viewstate.FindCityViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindCity(
    viewModel: FindCityViewModel = hiltViewModel(),
    onCitySelected: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    val viewState by viewModel.viewState.collectAsState()
    var city by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadCities()
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = modalBottomSheetState
        )
        {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize()
            ) {
                BottomSheetCityInfo(
                    city = city,
                    onCancelClick = { showBottomSheet = false },
                    onAddClick = {
                        viewModel.onCitySelected(city)
                        onCitySelected(city)
                        showBottomSheet = false
                    }
                )
            }
        }
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if ((viewState as? FindCityViewState.Success)?.citiesList?.isNotEmpty() == true){
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(48.dp)
                     ){
                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                       contentDescription = "Назад"
                   )
                }
            }
            SearchField(
                value = city,
                onValueChange = { city = it },
                onSearch = {
                    if (city.isNotBlank()) {
                        showBottomSheet = true
                    }
                }
            )
        }
        when (val state = viewState) {
            is FindCityViewState.Success -> {
                CitiesList(
                    cities = state.citiesList,
                    onDelete = { city ->
                        viewModel.onDeleteCityClick(city)
                    },
                    onSelected = { citySelected ->
                        city = citySelected.city
                        citySelected.id?.let { viewModel.saveCityIdToPref(it) }
                    }
                )
            }

            is FindCityViewState.Error -> {
                ErrorCard(
                    message = state.message,
                    onRetry = {
                        viewModel.loadCities()
                    }
                )
            }

            is FindCityViewState.Loading -> LoadingIndicator()
            else -> {}
        }
    }
}
