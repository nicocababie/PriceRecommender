package com.example.pricerecommender.ui.screen.purchase

import android.net.Uri
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.app.Purchase
import com.example.pricerecommender.ui.ApiUIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

data class PurchaseUIState(
    val purchase: Purchase = Purchase(name = "", address = "", products = emptyList()),
    val storeCoord: LatLng = LatLng(0.0, 0.0),
    val currentDepartment: String = "Select department",
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(storeCoord, 10f),
    val departments: List<String> = emptyList(),
    val imageUri: Uri = Uri.EMPTY,
    val apiState: ApiUIState<Int> = ApiUIState.Success(0),
    val receipt: List<Product> = emptyList(),
    val selectedProduct: Product = Product("", 0, 0.0, "", "")
)
