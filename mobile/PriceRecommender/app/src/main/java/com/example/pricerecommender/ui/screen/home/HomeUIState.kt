package com.example.pricerecommender.ui.screen.home

import com.example.pricerecommender.ui.ApiUIState

data class HomeUIState(
    val userId: String = "",
    val currentRange: Float = 0f,
    val currentAddress: String = "",
    val addresses: List<String> = emptyList(),
    val apiState: ApiUIState<Any?> = ApiUIState.Loading
)
