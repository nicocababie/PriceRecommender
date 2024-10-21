package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.entity.Address
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressDao: AddressDao): IAddressRepository {
    override suspend fun insert(name: String, lat: Double, lng: Double) {
        val newAddress = Address(address = name, lat = lat, lng = lng)
        val address = getAddressByName(name)
        if (address == null) {
            addressDao.insert(newAddress)
        }
    }

    override suspend fun delete(name: String) {
        val address = getAddressByName(name)
        address?.let{
            addressDao.delete(address)
        }
    }

    override fun getAllAddresses(): List<Address> {
        return addressDao.getAllAddresses()
    }

    override fun getAddressByName(name: String): Address? {
        return addressDao.getAddressByName(name)
    }
}