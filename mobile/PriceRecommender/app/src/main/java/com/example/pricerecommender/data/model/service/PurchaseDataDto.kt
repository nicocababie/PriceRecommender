package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseDataDto(
    @SerialName("storeName") val storeName: String,
    @SerialName("storeAddress") val storeAddress: String,
    @SerialName("listProducts") val products: List<ProductDto>,
    @SerialName("date") val date: String
)