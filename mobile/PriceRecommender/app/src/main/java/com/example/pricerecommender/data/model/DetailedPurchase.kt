package com.example.pricerecommender.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedPurchase(
    @SerialName("userId") val userId: String,
    @SerialName("storeName") val storeName: String,
    @SerialName("storeAddress") val storeAddress: String,
    @SerialName("storeLatitude") val storeLatitude: Double,
    @SerialName("storeLongitude") val storeLongitude: Double,
    @SerialName("listProducts") val listProducts: List<Product>
)
