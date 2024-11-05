package com.example.pricerecommender.ui.screen.home

import com.example.pricerecommender.data.model.app.AddressDetail
import com.example.pricerecommender.ui.ApiUIState

data class HomeUIState(
    val userId: String = "",
    val currentRange: Float = 0f,
    val currentAddress: AddressDetail = AddressDetail("", 0.0, 0.0),
    val addresses: List<AddressDetail> = emptyList(),
    val apiState: ApiUIState<Any?> = ApiUIState.Loading
)
