package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.ProductResponse

interface IProductRepository {
    suspend fun getProducts(): ProductResponse
}