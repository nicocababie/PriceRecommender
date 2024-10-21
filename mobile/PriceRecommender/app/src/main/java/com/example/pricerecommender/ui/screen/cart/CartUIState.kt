package com.example.pricerecommender.ui.screen.cart

import com.example.pricerecommender.data.model.CartProduct

data class CartUIState(
    val cart: List<CartProduct> = emptyList()
)
