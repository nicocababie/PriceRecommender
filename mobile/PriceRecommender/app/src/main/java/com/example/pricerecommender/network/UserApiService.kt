package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.service.UserDto
import retrofit2.http.GET

interface UserApiService {
    @GET("users")
    suspend fun getUserId(): UserDto
}