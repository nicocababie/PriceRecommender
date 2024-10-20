package com.example.pricerecommender.ui.screen.address

data class AddressUIState(
    val currentRange: Float = 0f,
    val currentAddress: String = "",
    val addresses: List<String> = emptyList()
)
