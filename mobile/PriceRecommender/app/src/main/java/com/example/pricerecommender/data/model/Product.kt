package com.example.pricerecommender.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("name") val name: String,
    @SerialName("amount") val amount: Int,
    @SerialName("price") val price: Double,
    @SerialName("brand") val brand: String,
    @SerialName("store") val store: String
) {
}