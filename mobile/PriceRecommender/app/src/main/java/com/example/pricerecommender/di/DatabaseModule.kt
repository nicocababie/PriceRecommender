package com.example.pricerecommender.di

import android.content.Context
import androidx.room.Room
import com.example.pricerecommender.data.database.PriceRecommenderDatabase
import com.example.pricerecommender.data.database.dao.AddressDao
import com.example.pricerecommender.data.database.dao.DepartmentDao
import com.example.pricerecommender.data.repository.AddressRepository
import com.example.pricerecommender.data.repository.DepartmentRepository
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import com.example.pricerecommender.data.repositoryInterface.IDepartmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {
    @Provides
    fun ProvideAddressRepository(addressDao: AddressDao): IAddressRepository = AddressRepository(addressDao)

    @Provides
    fun ProvideDepartmentRepository(departmentDao: DepartmentDao): IDepartmentRepository = DepartmentRepository(departmentDao)

    @Provides
    fun ProvideAddressDao(database: PriceRecommenderDatabase) = database.AddressDao()

    @Provides
    fun ProvideDepartmentDao(database: PriceRecommenderDatabase) = database.DepartmentDao()

    @Provides
    fun ProvideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            PriceRecommenderDatabase::class.java,
            "price_recommender"
        )
            .fallbackToDestructiveMigration()
            .build()

}