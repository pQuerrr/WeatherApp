package com.example.apitest.data.repositoryImpl

import com.example.apitest.data.remote.api.GeoApiService
import com.example.apitest.data.remote.response.GeoResponse
import com.example.apitest.domain.repository.GeoRepository
import javax.inject.Inject
import com.example.apitest.utils.Result

class GeoRepositoryImpl @Inject constructor(
    private val apiService:GeoApiService,
    private val apiKey: String
): GeoRepository {
    override suspend fun getCityName(lat: Double, lon: Double, limit: Long): Result<GeoResponse> {
        return try {
        val response = apiService.getCityName(lat, lon, limit, apiKey)
        if (response.isSuccessful) {
            response.body()?.firstOrNull()?.let { geoResponse ->
                Result.Success(geoResponse)
            } ?: Result.Error(Exception("Ошибка сервера"))
        } else {
            Result.Error(Exception("Ошибка : ${response.code()}"))
        }
    } catch (e: Exception){
        Result.Error(e)
        }
    }
}