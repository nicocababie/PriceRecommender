package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.CartProduct

interface ICartRepository {
    suspend fun add(
        cart: List<CartProduct>,
        userId: String,
    )

    suspend fun delete(
        cart: List<CartProduct>,
        userId: String,
    )

    suspend fun empty(
        userId: String
    )
}