package com.example.apitest.presentation.findcityscreen.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apitest.data.local.entities.CitiesInfoTuple

@Composable
fun CitiesListItem(
    modifier: Modifier = Modifier,
    city: CitiesInfoTuple,
    onDelete: () -> Unit,
    onSelect: () -> Unit
) {
    Row (
        modifier = modifier
            .height(48.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = city.city,
            textAlign = TextAlign.Left,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onSelect)
                .padding(vertical = 10.dp)
        )
        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(48.dp)
            ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Удаление",
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun CitiesListItemPreview() {
    CitiesListItem(
        city = CitiesInfoTuple(1,"Yalta"),
        onDelete = {}) {

    }
}