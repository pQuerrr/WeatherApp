package com.example.apitest.presentation.findcityscreen.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apitest.data.local.entities.CitiesInfoTuple

@Composable
fun CitiesList(
    modifier: Modifier = Modifier,
    cities: List<CitiesInfoTuple>,
    onDelete: (CitiesInfoTuple) -> Unit,
    onSelected: (CitiesInfoTuple) -> Unit
) {
    LazyColumn {
        items(
            items = cities,
            key = {it.id}
        ){city ->
            CitiesListItem(
                city = city,
                onDelete = { onDelete(city) },
                onSelect = { onSelected(city)}
            )
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