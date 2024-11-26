package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.service.BestRouteDto
import com.example.pricerecommender.data.model.service.ProductListDto
import com.example.pricerecommender.data.model.service.RouteDetailDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ProductListDto

    @POST("products/routes/{id}")
    suspend fun getBestRoute(@Path("id") userId: String, @Body route: RouteDetailDto): BestRouteDto
}