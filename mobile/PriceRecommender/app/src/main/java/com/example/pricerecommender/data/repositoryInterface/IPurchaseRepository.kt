package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.PurchaseReport
import com.google.android.gms.maps.model.LatLng

interface IPurchaseRepository {
    suspend fun add(
        userId: String,
        storeName: String,
        storeAddress: String,
        products: List<Product>,
        coord: LatLng
    )

    suspend fun getReport(userId: String): PurchaseReport
}