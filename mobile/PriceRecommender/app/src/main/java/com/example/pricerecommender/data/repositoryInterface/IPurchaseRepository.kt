package com.example.pricerecommender.data.repositoryInterface

import android.content.Context
import android.net.Uri
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.app.PurchaseData
import com.google.android.gms.maps.model.LatLng

interface IPurchaseRepository {
    suspend fun add(
        userId: String,
        storeName: String,
        storeAddress: String,
        products: List<Product>,
        coord: LatLng
    )

    suspend fun getReport(userId: String): List<PurchaseData>

    suspend fun addReceipt(
        imageUri: Uri,
        storeLat: Double,
        storeLng: Double,
        userId: String,
        context: Context
    ): List<Product>
}