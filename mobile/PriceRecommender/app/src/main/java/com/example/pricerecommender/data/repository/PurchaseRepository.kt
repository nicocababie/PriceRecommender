package com.example.pricerecommender.data.repository

import android.content.Context
import android.net.Uri
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.app.PurchaseData
import com.example.pricerecommender.data.model.service.DetailedPurchaseDto
import com.example.pricerecommender.data.model.service.ProductDto
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import com.example.pricerecommender.network.PurchaseApiService
import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException
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

    override suspend fun addReceipt(
        imageUri: Uri,
        storeLat: Double,
        storeLng: Double,
        userId: String,
        context: Context
    ): List<Product> {
        val file = uriToFile(context, imageUri) ?: throw IOException("File not found")

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("img", file.name, requestFile)

        val storeLatBody = storeLat.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val storeLngBody = storeLng.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())

        val result = purchaseApiService.addReceipt(
            img = imagePart,
            storeLatitude = storeLatBody,
            storeLongitude = storeLngBody,
            userId = userIdBody
        )

        return result.data.products.map {
            Product(it.name, it.amount, it.price, it.brand, it.store)
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File? {
        return if (uri.scheme == "file") {
            File(uri.path!!)
        } else {
            context.contentResolver.openInputStream(uri)?.let { inputStream ->
                val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                tempFile
            }
        }
    }

    private fun prepareImageForUpload(context: Context, uri: Uri): MultipartBody.Part? {
        val file = uriToFile(context, uri) ?: return null
        val requestFile: RequestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
}