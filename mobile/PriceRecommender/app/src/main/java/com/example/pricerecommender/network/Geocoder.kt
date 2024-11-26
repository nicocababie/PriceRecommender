package com.example.pricerecommender.network

import android.content.Context
import android.location.Geocoder
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale

fun geocodeLocation(context: Context, address: String, callback: (LatLng?) -> Unit) {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocationName(address, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            val location = addresses[0]
            callback(LatLng(location.latitude, location.longitude))
        } else {
            Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
            callback(null)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Geocode failed: ${e.message}", Toast.LENGTH_SHORT).show()
        callback(null)
    }
}

fun reverseGeocodeLocation(context: Context, latLng: LatLng, callback: (String?) -> Unit) {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (addresses != null && addresses.isNotEmpty()) {
            val address = addresses[0].getAddressLine(0)
            callback(address)
        } else {
            Toast.makeText(context, "Address not found", Toast.LENGTH_SHORT).show()
            callback(null)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Toast.makeText(context, "Reverse geocode failed: ${e.message}", Toast.LENGTH_SHORT).show()
        callback(null)
    }
}

fun parseLatLng(input: String): LatLng? {
    val latLngStr = input.split(",", limit = 2)
    return if (latLngStr.size == 2) {
        val lat = latLngStr[0].toDoubleOrNull()
        val lng = latLngStr[1].toDoubleOrNull()
        if (lat != null && lng != null) {
            LatLng(lat, lng)
        } else {
            null
        }
    } else {
        null
    }
}