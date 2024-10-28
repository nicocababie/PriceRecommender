package com.example.pricerecommender.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RouteDetail(
    @SerialName("addressLat") val lat: Double,
    @SerialName("addressLng") val lng: Double,
    @SerialName("range") val range: Int
)
