package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PurchaseReceiptDto(
    @SerialName("message") val message: String,
    @SerialName("product") val data: PurchaseDataDto
)