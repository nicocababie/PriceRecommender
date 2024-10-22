package com.example.pricerecommender.ui.screen.address

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

data class AddressUIState(
    val currentAddress: String = "",
    val currentDepartment: String = "Select department",
    var currentCoord: LatLng = LatLng(0.0, 0.0),
    val cameraPosition: CameraPosition = CameraPosition.fromLatLngZoom(currentCoord, 10f),
    val departments: List<String> = emptyList()
)
