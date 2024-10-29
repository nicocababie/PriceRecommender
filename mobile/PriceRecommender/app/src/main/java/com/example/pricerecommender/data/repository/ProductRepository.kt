package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.BestResult
import com.example.pricerecommender.data.model.ProductResponse
import com.example.pricerecommender.data.model.RouteDetail
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import com.example.pricerecommender.network.ProductApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApiService: ProductApiService
): IProductRepository {
    override suspend fun getProducts(): ProductResponse {
        return productApiService.getProducts()
    }

    override suspend fun getBestRoute(
        userId: String,
        addressLat: Double,
        addressLng: Double,
        range: Int,
    ): List<BestResult> {
        val detail = RouteDetail(addressLat, addressLng, (range * 1000))
        return productApiService.getBestRoute(userId, detail).data
    }
}