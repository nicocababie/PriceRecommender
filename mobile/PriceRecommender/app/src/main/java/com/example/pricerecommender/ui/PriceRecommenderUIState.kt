package com.example.pricerecommender.ui

data class PriceRecommenderUIState(
    val currentRange: Float = 0f,
    val currentAddress: String = "",
    val addresses: List<String> = emptyList()
)
