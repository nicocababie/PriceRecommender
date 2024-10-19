package com.example.pricerecommender.ui.screen.bestRoute

import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

data class BestRouteUIState(
    val cameraPosition: CameraPosition =
        CameraPosition.fromLatLngZoom(LatLng(-34.901112, -56.164532), 14f),
    var isMapLoaded: Boolean = false,
    var markers: List<LatLng> = listOf(
        LatLng(-34.903719688665454, -56.19058160393166),
        LatLng(-34.90415131623156, -56.17070821584382)
    )
)
