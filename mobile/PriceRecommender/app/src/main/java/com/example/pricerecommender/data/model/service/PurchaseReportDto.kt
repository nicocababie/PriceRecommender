package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PurchaseReportDto(
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<PurchaseDataDto>
)