package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.DetailedPurchase
import retrofit2.http.Body
import retrofit2.http.POST

interface PurchaseApiService {
    @POST("purchases")
    suspend fun add(@Body purchase: DetailedPurchase)
}