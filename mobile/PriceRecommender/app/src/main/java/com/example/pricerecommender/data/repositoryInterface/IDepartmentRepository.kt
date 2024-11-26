package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.database.entity.Department

interface IDepartmentRepository {
    fun getAllDepartments(): List<Department>
}