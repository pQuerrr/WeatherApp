package com.example.apitest.presentation.mainscreen.view

import android.text.TextPaint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apitest.data.local.entities.CitiesInfoTuple

@Composable
fun FindCityItem(
    modifier: Modifier = Modifier,
    onDeleteCityButtonClick:() -> Unit,
    onChooseCityButtonClick:() -> Unit,
    cityItem: CitiesInfoTuple
) {
    Row() {
        TextButton(
            shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp,
        ),
            modifier = Modifier
                .weight(9f),
            onClick = { onChooseCityButtonClick() }) {
            Text(
                text = cityItem.city,
                color = Color.Black,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            ) }
        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = { onDeleteCityButtonClick() }) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "Удаление",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun FindCityItemPreview() {
    FindCityItem(
        onDeleteCityButtonClick = {},
        onChooseCityButtonClick = {},
        cityItem = CitiesInfoTuple(
            city = "Moscow",
            id = 1
        )
    )
}
