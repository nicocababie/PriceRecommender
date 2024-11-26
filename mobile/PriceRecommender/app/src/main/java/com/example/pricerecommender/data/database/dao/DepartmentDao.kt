package com.example.pricerecommender.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.pricerecommender.data.database.entity.Department

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM department")
    fun getAllDepartments(): List<Department>
}