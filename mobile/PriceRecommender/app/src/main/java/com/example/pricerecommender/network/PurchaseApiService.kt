package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.DetailedPurchase
import com.example.pricerecommender.data.model.PurchaseReport
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PurchaseApiService {
    @POST("purchases")
    suspend fun add(@Body purchase: DetailedPurchase)

    @GET("stats/{id}")
    suspend fun getReport(@Path("id") userId: String): PurchaseReport
}