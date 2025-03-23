package com.example.apitest.domain.repository

import com.example.apitest.data.local.entities.CitiesDbEntity
import com.example.apitest.data.local.entities.CitiesInfoTuple

interface CitiesRepository {
    suspend fun getUniqueCity(cityName: String): CitiesInfoTuple?
    suspend fun insertNewCitiesData(citiesDbEntity : CitiesDbEntity)
    suspend fun getAllCitiesData(): List<CitiesInfoTuple>
    suspend fun deleteCitiesDataById(id: Long)
    suspend fun getCityById(id: Long): CitiesInfoTuple
}