package com.example.apitest.presentation.components.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    onSearch: () -> Unit,
    onValueChange: (String) -> Unit
) {
OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text("Введите название города")},
    modifier = Modifier
        .fillMaxWidth(),
    trailingIcon = {
        IconButton(
            onClick = { onSearch() },
            enabled = value.isNotBlank()
            ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Поиск"
            )
        }
    },
    singleLine = true
)
}