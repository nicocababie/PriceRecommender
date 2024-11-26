package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.app.User
import com.example.pricerecommender.data.repositoryInterface.IUserRepository
import com.example.pricerecommender.network.UserApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService
): IUserRepository {
    override suspend fun getUserId(): User {
        return User(userApiService.getUserId().id)
    }
}