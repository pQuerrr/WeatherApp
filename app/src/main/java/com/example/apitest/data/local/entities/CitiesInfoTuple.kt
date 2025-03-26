package com.example.apitest.data.local.entities

import androidx.room.ColumnInfo



data class CitiesInfoTuple(
    val id: Long,
    @ColumnInfo(name = "city") val city: String,
//    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)
