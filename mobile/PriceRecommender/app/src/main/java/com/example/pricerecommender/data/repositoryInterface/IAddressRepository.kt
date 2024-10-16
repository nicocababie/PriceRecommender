package com.example.pricerecommender.data.repositoryInterface

interface IAddressRepository {
    suspend fun insert(address: String)
}