package com.example.pricerecommender.ui.screen.cart

import com.example.pricerecommender.data.model.app.CartProduct
import com.example.pricerecommender.ui.ApiUIState

data class CartUIState(
    val userId: String = "",
    val cart: List<CartProduct> = emptyList(),
    val currentName: String = "Name",
    val currentAmount: Int = 0,
    val currentBrand: String = "Brand",
    val apiState: ApiUIState<List<CartProduct>> = ApiUIState.Loading
)
