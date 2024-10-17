package com.example.pricerecommender.ui

data class PriceRecommenderUIState(
    val currentAddress: String = "",
    val addresses: List<String> = emptyList()
)
