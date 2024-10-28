package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.database.entity.Address
import com.example.pricerecommender.data.model.AddressDetail


interface IAddressRepository {
    suspend fun insert(name: String, lat: Double, lng: Double): AddressDetail
    suspend fun delete(name: String)
    fun getAllAddresses(): List<AddressDetail>
    fun getAddressByName(name: String): AddressDetail?
    fun getAddressEntityByName(name: String): Address?
}