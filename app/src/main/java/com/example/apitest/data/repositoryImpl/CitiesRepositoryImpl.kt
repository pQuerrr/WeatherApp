package com.example.apitest.data.repositoryImpl

import com.example.apitest.data.local.dao.CitiesDao
import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.data.local.entities.CitiesDbEntity
import com.example.apitest.domain.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val citiesDao: CitiesDao
): CitiesRepository {
    override suspend fun getUniqueCity(cityName: String): CitiesInfoTuple?{
        return withContext(Dispatchers.IO){
            return@withContext citiesDao.getUniqueCity(cityName)
        }
    }

    override suspend fun insertNewCitiesData(citiesDbEntity : CitiesDbEntity){
        withContext(Dispatchers.IO){
            citiesDao.insertNewCitiesData(citiesDbEntity)
        }
    }

    override suspend fun getAllCitiesData(): List<CitiesInfoTuple>{
        return withContext(Dispatchers.IO){
            return@withContext citiesDao.getAllCitiesData()
        }
    }

    override suspend fun deleteCitiesDataById(id: Long){
        withContext(Dispatchers.IO){
            citiesDao.deleteCitiesDataById(id)
        }
    }
    override suspend fun getCityById(id: Long): CitiesInfoTuple{
        return withContext(Dispatchers.IO){
            return@withContext citiesDao.getCityById(id)
        }
    }


}