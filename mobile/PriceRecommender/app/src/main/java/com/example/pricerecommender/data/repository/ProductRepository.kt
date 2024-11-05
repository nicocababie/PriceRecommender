package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.app.BestResult
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.service.RouteDetailDto
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
        val detail = RouteDetailDto(addressLat, addressLng, (range * 1000).toInt())
        return productApiService.getBestRoute(userId, detail).data.map {
            BestResult(it.storeName, it.storeLat, it.storeLng, it.productName, it.price)
        }
    }
}