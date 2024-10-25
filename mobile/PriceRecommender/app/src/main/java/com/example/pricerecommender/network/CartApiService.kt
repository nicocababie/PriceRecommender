package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.CartProduct
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartApiService {
    @POST("carts/{id}")
    suspend fun add(@Path("id") userId: String, @Body cart: List<CartProduct>)

    @PUT("carts/{id}")
    suspend fun delete(@Path("id") userId: String, @Body cart: List<CartProduct>)
}