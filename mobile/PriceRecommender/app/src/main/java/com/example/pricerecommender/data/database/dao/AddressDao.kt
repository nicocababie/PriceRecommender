package com.example.pricerecommender.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.pricerecommender.data.database.entity.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(address: Address)
}