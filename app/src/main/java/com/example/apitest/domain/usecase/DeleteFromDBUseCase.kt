package com.example.apitest.domain.usecase

import com.example.apitest.data.local.entities.CitiesInfoTuple
import com.example.apitest.domain.repository.CitiesRepository
import javax.inject.Inject

class DeleteFromDBUseCase @Inject constructor(
    private val citiesRepository: CitiesRepository
){
    suspend operator fun invoke(city: CitiesInfoTuple) {
        citiesRepository.deleteCitiesDataById(city.id)
    }
}