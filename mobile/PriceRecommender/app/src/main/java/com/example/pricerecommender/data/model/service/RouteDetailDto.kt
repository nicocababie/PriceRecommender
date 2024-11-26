package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteDetailDto(
    @SerialName("addressLat") val lat: Double,
    @SerialName("addressLng") val lng: Double,
    @SerialName("range") val range: Int
)
