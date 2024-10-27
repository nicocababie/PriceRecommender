package com.example.pricerecommender.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseReport(
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<PurchaseData>
)

@Serializable
data class PurchaseData(
    @SerialName("storeName") val storeName: String,
    @SerialName("storeAddress") val storeAddress: String,
    @SerialName("listProducts") val products: List<Product>,
    @SerialName("date") val date: String
)