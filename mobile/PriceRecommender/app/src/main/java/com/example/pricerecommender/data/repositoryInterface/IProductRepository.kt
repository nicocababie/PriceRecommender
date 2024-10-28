package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.BestResult
import com.example.pricerecommender.data.model.ProductResponse

interface IProductRepository {
    suspend fun getProducts(): ProductResponse
    suspend fun getBestRoute(
        userId: String,
        addressLat: Double,
        addressLng: Double,
        range: Int
    ): List<BestResult>
}