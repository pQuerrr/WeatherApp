package com.example.apitest.domain.usecase

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.domain.repository.CitiesRepository
import javax.inject.Inject
import com.example.apitest.utils.Result

class LoadCitiesListUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    suspend operator fun invoke(): Result<List<CitiesInfoTuple>>{
        return try {
            val citiesList = citiesRepository.getAllCitiesData()
            Result.Success(citiesList)
        } catch (e: Exception){
            Result.Error(Exception("Ошибка при загрузке данных из БД: ${e.message}"))
        }
    }
}

