package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.ProductResponse
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import com.example.pricerecommender.network.ProductApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApiService: ProductApiService
): IProductRepository {
    override suspend fun getProducts(): ProductResponse {
        return productApiService.getProducts()
    }
}