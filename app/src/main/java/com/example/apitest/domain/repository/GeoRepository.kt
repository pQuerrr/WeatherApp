package com.example.apitest.domain.repository

import com.example.apitest.data.remote.response.GeoResponse
import com.example.apitest.utils.Result

interface GeoRepository {
    suspend fun getCityName(lat: Long, lon: Long, limit: Long): Result<GeoResponse>
}