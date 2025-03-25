package com.example.apitest.presentation.mainscreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.remote.response.City

@Composable
fun CitiesListItem(
    modifier: Modifier = Modifier,
    city: CitiesInfoTuple,
    onDelete: () -> Unit,
    onSelect: () -> Unit
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ){
        Text(
            text = city.city,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onSelect)
        )
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Удаление"
            )
        }
    }
}