package com.example.pricerecommender.data.model.app

data class PurchaseData(
    val storeName: String,
    val storeAddress: String,
    val products: List<Product>,
    val date: String
)