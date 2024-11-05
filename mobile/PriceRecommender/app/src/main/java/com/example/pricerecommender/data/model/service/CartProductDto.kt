package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartProductDto(
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String,
    @SerialName("amount") val amount: Int,
    @SerialName("brand") val  brand: String,
)
