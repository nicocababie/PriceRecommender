package com.example.pricerecommender.ui.screen.cart

import com.example.pricerecommender.data.model.CartProduct

data class CartUIState(
    val userId: String = "",
    val cart: List<CartProduct> = emptyList(),
    val currentName: String = "",
    val currentAmount: Int = 0,
    val currentBrand: String = "",
)
