package com.example.pricerecommender.data.model

import com.example.pricerecommender.data.model.app.Product

data class Purchase(
    val name: String,
    val address: String,
    val products: List<Product>
) {
}