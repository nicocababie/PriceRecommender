package com.example.pricerecommender.di

import com.example.pricerecommender.data.repository.PurchaseRepository
import com.example.pricerecommender.data.repository.UserRepository
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import com.example.pricerecommender.data.repositoryInterface.IUserRepository
import com.example.pricerecommender.env
import com.example.pricerecommender.network.PurchaseApiService
import com.example.pricerecommender.network.UserApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideBaseUrl(): String {
        return env.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePurchaseApiService(retrofit: Retrofit): PurchaseApiService {
        return retrofit.create(PurchaseApiService::class.java)
    }

    @Provides
    fun provideUserRepository(userApiService: UserApiService): IUserRepository =
        UserRepository(userApiService)

    @Provides
    fun providePurchaseRepository(purchaseApiService: PurchaseApiService): IPurchaseRepository =
        PurchaseRepository(purchaseApiService)
}