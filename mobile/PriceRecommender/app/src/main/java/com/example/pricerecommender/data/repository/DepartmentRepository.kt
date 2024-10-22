package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.database.dao.DepartmentDao
import com.example.pricerecommender.data.database.entity.Department
import com.example.pricerecommender.data.repositoryInterface.IDepartmentRepository
import javax.inject.Inject

class DepartmentRepository @Inject constructor(private val departmentDao: DepartmentDao): IDepartmentRepository {
    override fun getAllDepartments(): List<Department> {
        return departmentDao.getAllDepartments()
    }
}