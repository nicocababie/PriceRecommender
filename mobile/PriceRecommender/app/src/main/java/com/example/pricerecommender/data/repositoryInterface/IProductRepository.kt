package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.BestResult
import com.example.pricerecommender.data.model.app.Product

interface IProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getBestRoute(
        userId: String,
        addressLat: Double,
        addressLng: Double,
        range: Float
    ): List<BestResult>
}