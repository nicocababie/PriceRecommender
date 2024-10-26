package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.ProductResponse
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}