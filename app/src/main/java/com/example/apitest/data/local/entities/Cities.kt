package com.example.apitest.data.local.entities

data class Cities(
    val id: Long,
    val city: String
) {
    fun toCitiesDbEntity(): CitiesDbEntity = CitiesDbEntity(
        id = 0,
        city = city
    )
}