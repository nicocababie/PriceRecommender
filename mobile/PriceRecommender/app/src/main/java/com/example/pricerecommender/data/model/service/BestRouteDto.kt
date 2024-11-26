package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestRouteDto(
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<BestResultDto>
)

@Serializable
data class BestResultDto(
    @SerialName("storeName") val storeName: String,
    @SerialName("storeLatitude") val storeLat: Double,
    @SerialName("storeLongitude") val storeLng: Double,
    @SerialName("productName") val productName: String,
    @SerialName("price") val price: Double
)