package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.BestResult
import com.example.pricerecommender.data.model.RouteDetail
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import com.example.pricerecommender.network.ProductApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApiService: ProductApiService
): IProductRepository {
    override suspend fun getProducts(): List<Product> {
        return productApiService.getProducts().data.map {
            Product(it.name, it.amount, it.price, it.brand, it.store)
        }
    }

    override suspend fun getBestRoute(
        userId: String,
        addressLat: Double,
        addressLng: Double,
        range: Float,
    ): List<BestResult> {
        val detail = RouteDetail(addressLat, addressLng, (range * 1000).toInt())
        return productApiService.getBestRoute(userId, detail).data
    }
}