package com.example.pricerecommender.data.model.app

data class BestResult(
    val storeName: String,
    val storeLat: Double,
    val storeLng: Double,
    val productName: String,
    val price: Double
)