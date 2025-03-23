package com.example.apitest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.apitest.data.local.entities.CitiesDbEntity
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.utils.Result

@Dao
interface CitiesDao {
    @Insert
    fun insertNewCitiesData(cities: CitiesDbEntity)

    @Query("SELECT id, city FROM cities")
    fun getAllCitiesData(): List<CitiesInfoTuple>

    @Query("DELETE FROM cities WHERE id = :citiesId")
    fun deleteCitiesDataById(citiesId: Long)

    @Query("SELECT * FROM cities where city = :cityName")
    fun getUniqueCity(cityName: String): CitiesInfoTuple?

    @Query("SELECT * FROM cities where id = :cityId")
    fun getCityById(cityId: Long) : CitiesInfoTuple

}