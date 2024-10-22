package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.User

interface IUserRepository {
    suspend fun getUserId(): User
}