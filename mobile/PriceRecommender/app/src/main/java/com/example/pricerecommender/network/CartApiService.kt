package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.service.CartProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApiService {
    @POST("carts/{id}")
    suspend fun add(@Path("id") userId: String, @Body cart: List<CartProductDto>)

    @PUT("carts/{id}")
    suspend fun update(@Path("id") userId: String, @Body cart: List<CartProductDto>)

    @GET("carts/{id}")
    suspend fun getCart(@Path("id") userId: String): List<CartProductDto>
}