package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.DetailedPurchase
import com.example.pricerecommender.data.model.Product
import com.example.pricerecommender.data.model.PurchaseReport
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import com.example.pricerecommender.network.PurchaseApiService
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class PurchaseRepository @Inject constructor(
    private val purchaseApiService: PurchaseApiService
): IPurchaseRepository {
    override suspend fun add(
        userId: String,
        storeName: String,
        storeAddress: String,
        products: List<Product>,
        coord: LatLng
    ) {
        val detailedPurchase = DetailedPurchase(
            userId = userId,
            storeName = storeName,
            storeAddress = storeAddress,
            storeLatitude = coord.latitude,
            storeLongitude = coord.longitude,
            listProducts = products
        )
        purchaseApiService.add(detailedPurchase)
    }

    override suspend fun getReport(userId: String): PurchaseReport {
        return purchaseApiService.getReport(userId)
    }

}