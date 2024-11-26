package com.example.pricerecommender.data.model.service

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName(value = "userId") val id: String
)