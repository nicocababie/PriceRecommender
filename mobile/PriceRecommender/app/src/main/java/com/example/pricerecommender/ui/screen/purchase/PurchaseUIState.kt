package com.example.pricerecommender.ui.screen.purchase

import com.example.pricerecommender.data.model.Purchase
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

data class PurchaseUIState(
    val purchase: Purchase = Purchase(name = "", address = "", products = emptyList()),
    val storeCoord: LatLng = LatLng(0.0, 0.0),
    val currentDepartment: String = "Select department",
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(storeCoord, 10f),
    val departments: List<String> = emptyList()
)
