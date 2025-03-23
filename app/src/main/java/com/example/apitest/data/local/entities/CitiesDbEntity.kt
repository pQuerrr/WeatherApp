package com.example.apitest.data.local.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cities",
    indices = [Index(value = ["city"], unique = true)]
)
data class CitiesDbEntity(
    @PrimaryKey(autoGenerate = true) val id : Long? = null,
    @ColumnInfo(name = "city") val city : String
)
