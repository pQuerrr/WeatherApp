package com.example.apitest.data.remote.api

import com.example.apitest.data.remote.response.GeoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApiService {
    @GET("geo/1.0/reverse")
    suspend fun getCityName(
        @Query("lat") latitude : Long,
        @Query("lon") longitude : Long,
        @Query("limit") limit : Long,
        @Query("appid") apiKey: String
    ) : Response<List<GeoResponse>>
}