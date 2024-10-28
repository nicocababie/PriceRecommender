package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.entity.Address
import com.example.pricerecommender.data.model.AddressDetail
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressDao: AddressDao): IAddressRepository {
    override suspend fun insert(name: String, lat: Double, lng: Double): AddressDetail {
        val result = getAddressByName(name)
        val address = Address(address = name, lat = lat, lng = lng)
        if (result == null) {
            addressDao.insert(address)
            return AddressDetail(name, lat, lng)
        }
        return AddressDetail("", 0.0, 0.0)
    }

    override suspend fun delete(name: String) {
        val address = getAddressEntityByName(name)
        if (address != null) {
            addressDao.delete(address)
        }
    }

    override fun getAllAddresses(): List<AddressDetail> {
        val result = addressDao.getAllAddresses()
        val addressDetails = result.map {
            AddressDetail(it.address, it.lat, it.lng)
        }
        return addressDetails
    }

    override fun getAddressByName(name: String): AddressDetail? {
        val result = addressDao.getAddressByName(name)
        return result?.let{ AddressDetail(result.address, result.lat, result.lng) }
    }

    override fun getAddressEntityByName(name: String): Address? {
        return addressDao.getAddressByName(name)
    }
}