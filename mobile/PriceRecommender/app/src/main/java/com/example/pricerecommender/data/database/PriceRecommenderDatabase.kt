package com.example.pricerecommender.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.dao.DepartmentDao
import com.example.pricerecommender.data.database.entity.Address
import com.example.pricerecommender.data.database.entity.Department

@Database(
    entities = [Address::class, Department::class],
    version = 4,
    exportSchema = false)
abstract class PriceRecommenderDatabase : RoomDatabase() {
    abstract fun AddressDao(): AddressDao
    abstract fun DepartmentDao(): DepartmentDao
}