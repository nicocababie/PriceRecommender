package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.CartProduct
import com.example.pricerecommender.data.repositoryInterface.ICartRepository
import com.example.pricerecommender.network.CartApiService
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val cartApiService: CartApiService
): ICartRepository {
    override suspend fun add(
        cart: List<CartProduct>,
        userId: String,
    ) {
        cartApiService.add(userId, cart)
    }

    override suspend fun delete(
        cart: List<CartProduct>,
        userId: String
    ) {
        cartApiService.delete(userId, cart)
    }

    override suspend fun empty(userId: String) {
        cartApiService.delete(userId, emptyList())
    }
}