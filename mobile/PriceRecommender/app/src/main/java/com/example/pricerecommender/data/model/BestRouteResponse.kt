package com.example.pricerecommender.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BestRouteResponse(
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<BestResult>
)

@Serializable
data class BestResult(
    @SerialName("storeName") val storeName: String,
    @SerialName("storeLatitude") val storeLat: Double,
    @SerialName("storeLongitude") val storeLng: Double,
    @SerialName("productName") val productName: String,
    @SerialName("price") val price: Double
)