package com.example.pricerecommender.data.model.app

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String,
    val amount: Int,
    val price: Double,
    val brand: String,
    val store: String
) {
}