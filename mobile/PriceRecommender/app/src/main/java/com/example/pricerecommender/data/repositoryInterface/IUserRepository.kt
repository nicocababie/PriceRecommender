package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.app.User

interface IUserRepository {
    suspend fun getUserId(): User
}