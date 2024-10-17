package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.database.entity.Address


interface IAddressRepository {
    suspend fun insert(name: String)
    suspend fun delete(name: String)
    fun getAllAddresses(): List<Address>
    fun getAddressByName(name: String): Address
}