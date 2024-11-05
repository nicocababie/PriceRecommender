package com.example.pricerecommender.data.repository

import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.app.PurchaseData
import com.example.pricerecommender.data.model.service.DetailedPurchaseDto
import com.example.pricerecommender.data.model.service.ProductDto
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
        val detailedPurchase = DetailedPurchaseDto(
            userId = userId,
            storeName = storeName,
            storeAddress = storeAddress,
            storeLatitude = coord.latitude,
            storeLongitude = coord.longitude,
            listProducts = products.map {
                ProductDto(it.name, it.amount, it.price, it.brand, it.store)
            }
        )
        purchaseApiService.add(detailedPurchase)
    }

    override suspend fun getReport(userId: String): List<PurchaseData> {
        return purchaseApiService.getReport(userId).data.map { purchase ->
            PurchaseData(
                purchase.storeName,
                purchase.storeAddress,
                purchase.products.map { Product(it.name, it.amount, it.price, it.brand, it.store) },
                purchase.date)
        }
    }

}