package com.example.apitest.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoResponse(
    @SerialName("name") val name: String,
    @SerialName("local_names") val localNames: Map<String, String>? = null,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("country") val country: String? = null,
    @SerialName("state") val state: String? = null
)