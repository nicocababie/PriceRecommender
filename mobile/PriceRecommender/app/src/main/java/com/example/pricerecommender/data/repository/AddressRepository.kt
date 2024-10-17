package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.entity.Address
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressDao: AddressDao): IAddressRepository {
    override suspend fun insert(name: String) {
        val address = Address(address = name)
        addressDao.insert(address)
    }

    override suspend fun delete(name: String) {
        val address = getAddressByName(name)
        addressDao.delete(address)
    }

    override fun getAllAddresses(): List<Address> {
        return addressDao.getAllAddresses()
    }

    override fun getAddressByName(name: String): Address {
        return addressDao.getAddressByName(name)
    }
}