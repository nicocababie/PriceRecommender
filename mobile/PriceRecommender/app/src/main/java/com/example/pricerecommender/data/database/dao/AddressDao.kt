package com.example.pricerecommender.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pricerecommender.data.database.entity.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(address: Address)

    @Delete
    fun delete(address: Address)

    @Query("SELECT * FROM address")
    fun getAllAddresses(): List<Address>

    @Query("SELECT * FROM address WHERE address = :name")
    fun getAddressByName(name: String): Address?
}