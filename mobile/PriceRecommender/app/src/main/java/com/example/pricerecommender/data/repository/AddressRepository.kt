package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.entity.Address
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressDao: AddressDao): IAddressRepository {
    override suspend fun insert(address: String) {
        val newAddress = Address(address = address)
        addressDao.insert(newAddress)
    }
}