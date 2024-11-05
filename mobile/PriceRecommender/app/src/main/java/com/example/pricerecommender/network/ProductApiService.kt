package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.BestRouteResponse
import com.example.pricerecommender.data.model.RouteDetail
import com.example.pricerecommender.data.model.service.ProductListDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ProductListDto

    @POST("products/routes/{id}")
    suspend fun getBestRoute(@Path("id") userId: String, @Body route: RouteDetail): BestRouteResponse
}