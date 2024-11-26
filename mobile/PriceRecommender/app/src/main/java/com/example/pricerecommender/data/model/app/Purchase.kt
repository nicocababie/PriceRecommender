package com.example.pricerecommender.data.model.app

data class Purchase(
    val name: String,
    val address: String,
    val products: List<Product>
)