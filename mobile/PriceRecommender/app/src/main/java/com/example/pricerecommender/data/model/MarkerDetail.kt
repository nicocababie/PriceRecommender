package com.example.pricerecommender.data.model

import com.google.maps.android.compose.MarkerState

data class MarkerDetail(
    val storeName: String,
    val storeLatLng: MarkerState,
    val productName: String,
    val productPrice: Double
)
