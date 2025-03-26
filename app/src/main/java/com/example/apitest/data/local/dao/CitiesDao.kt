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

    @Query("SELECT * FROM cities WHERE city = :cityName")
    fun getUniqueCity(cityName: String): CitiesInfoTuple?

    @Query("SELECT * FROM cities WHERE id = :cityId")
    fun getCityById(cityId: Long) : CitiesInfoTuple

//    @Query("SELECT * FROM cities WHERE isFavorite = 1")
//    fun getFavorites(): List<CitiesInfoTuple>
//
//    @Query("UPDATE cities SET isFavorite = :isFavorite WHERE id = :cityId")
//    fun setFavorite(cityId: Long, isFavorite: Boolean)
}