package com.example.pricerecommender.network

import com.example.pricerecommender.data.model.service.DetailedPurchaseDto
import com.example.pricerecommender.data.model.service.PurchaseReportDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PurchaseApiService {
    @POST("purchases")
    suspend fun add(@Body purchase: DetailedPurchaseDto)

    @GET("stats/{id}")
    suspend fun getReport(@Path("id") userId: String): PurchaseReportDto

    @Multipart
    @POST("purchasesPictures")
    suspend fun addReceipt(
        @Part img: MultipartBody.Part,
        @Part("storeLatitude") storeLatitude: RequestBody,
        @Part("storeLongitude") storeLongitude: RequestBody,
        @Part("userId") userId: RequestBody
    )
}