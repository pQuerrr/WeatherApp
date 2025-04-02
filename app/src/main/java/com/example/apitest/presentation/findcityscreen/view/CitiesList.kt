package com.example.apitest.presentation.findcityscreen.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.data.local.entities.CitiesInfoTuple
import kotlinx.coroutines.delay

@Composable
fun CitiesList(
    modifier: Modifier = Modifier,
    cities: List<CitiesInfoTuple>,
    onDelete:  (CitiesInfoTuple) -> Unit,
    onSelected: (CitiesInfoTuple) -> Unit
) {
    LazyColumn {
        items(
            items = cities,
            key = { it.id!! }
        ){city ->
            var isVisable by remember { mutableStateOf(true) }

            LaunchedEffect(isVisable) {
                if (!isVisable){
                    delay(300)
                    onDelete(city)
                }
            }

            AnimatedVisibility(
                visible = isVisable,
                exit =  fadeOut() + scaleOut()
            ) {

                CitiesListItem(
                    city = city,
                    onDelete = {
                        isVisable = false
                               },
                    onSelect = { onSelected(city)}
                )
            }
        }
    }
}

@Preview
@Composable
private fun CitiesListPreview() {
    CitiesList(
        cities = listOf(CitiesInfoTuple(1, "Yalta"),CitiesInfoTuple(2,"Moscow")),
        onDelete = {},
        onSelected = {})
}