package com.example.pricerecommender.data.model

data class Purchase(
    val name: String,
    val address: String,
    val products: List<Product>
) {
}