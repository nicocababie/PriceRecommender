package com.example.pricerecommender.ui.screen.bestRoute

import com.example.pricerecommender.data.model.MarkerDetail
import com.example.pricerecommender.ui.ApiUIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

data class BestRouteUIState(
    val cameraPosition: CameraPosition =
        CameraPosition.fromLatLngZoom(LatLng(-34.901112, -56.164532), 14f),
    var isMapLoaded: Boolean = false,
    var details: List<MarkerDetail> = emptyList(),
    val apiState: ApiUIState<List<MarkerDetail>> = ApiUIState.Loading
)
