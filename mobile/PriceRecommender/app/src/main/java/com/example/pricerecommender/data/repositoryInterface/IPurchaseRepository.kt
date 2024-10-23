package com.example.pricerecommender.data.repositoryInterface

import com.example.pricerecommender.data.model.Product
import com.google.android.gms.maps.model.LatLng

interface IPurchaseRepository {
    suspend fun add(
        userId: String,
        storeName: String,
        storeAddress: String,
        products: List<Product>,
        coord: LatLng
    )
}