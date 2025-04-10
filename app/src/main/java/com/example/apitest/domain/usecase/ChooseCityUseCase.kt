package com.example.apitest.domain.usecase

import com.example.apitest.data.local.entities.CitiesDbEntity
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.domain.repository.CitiesRepository
import java.security.PrivateKey
import javax.inject.Inject

class ChooseCityUseCase @Inject constructor (
    private val citiesRepository: CitiesRepository
){
 suspend operator fun invoke(cityName: String){
     val cityId = citiesRepository.getUniqueCity(cityName)?.id
     if (cityId == null){
         val city = CitiesDbEntity(city = cityName)
         citiesRepository.insertNewCitiesData(city)
     }
 }
}