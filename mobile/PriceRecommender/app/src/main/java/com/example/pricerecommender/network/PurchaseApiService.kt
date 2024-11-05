package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.service.DetailedPurchaseDto
import com.example.pricerecommender.data.model.service.PurchaseReportDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PurchaseApiService {
    @POST("purchases")
    suspend fun add(@Body purchase: DetailedPurchaseDto)

    @GET("stats/{id}")
    suspend fun getReport(@Path("id") userId: String): PurchaseReportDto
}