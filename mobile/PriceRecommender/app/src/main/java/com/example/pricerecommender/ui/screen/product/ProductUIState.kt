package com.example.pricerecommender.ui.screen.product

import com.example.pricerecommender.data.model.app.Product

data class ProductUIState(
    val currentName: String = "",
    val currentAmount: Int = 0,
    val currentPrice: Double = 0.0,
    val currentBrand: String = "",
    val products: List<Product> = emptyList()
)
