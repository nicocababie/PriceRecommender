package com.example.pricerecommender.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.entity.Address

@Database(
    entities = [Address::class],
    version = 3,
    exportSchema = false)
abstract class PriceRecommenderDatabase : RoomDatabase() {
    abstract fun AddressDao(): AddressDao
}