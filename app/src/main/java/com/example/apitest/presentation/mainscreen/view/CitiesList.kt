package com.example.apitest.presentation.mainscreen.view

import android.telephony.AccessNetworkConstants.UtranBand
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

