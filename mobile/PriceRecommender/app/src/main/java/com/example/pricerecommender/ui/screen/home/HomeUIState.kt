package com.example.pricerecommender.ui.screen.home

data class HomeUIState(
    val currentRange: Float = 0f,
    val currentAddress: String = "",
    val addresses: List<String> = emptyList(),
    val departments: List<String> = emptyList()
)
