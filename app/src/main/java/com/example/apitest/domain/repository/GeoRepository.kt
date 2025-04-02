package com.example.apitest.domain.repository

import com.example.apitest.data.remote.response.GeoResponse
import com.example.apitest.utils.Result

interface GeoRepository {
    suspend fun getCityName(lat: Double, lon: Double, limit: Long): Result<GeoResponse>
}