package com.example.pricerecommender.ui.screen.address

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.services.geocodeLocation
import com.example.pricerecommender.services.parseLatLng
import com.example.pricerecommender.services.reverseGeocodeLocation
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun GeocodeExampleScreen() {
    val context = LocalContext.current
    var addressInput by remember { mutableStateOf("") }
    var latLngInput by remember { mutableStateOf("") }
    var geocodeResult by remember { mutableStateOf("Enter an address") }
    var reverseGeocodeResult by remember { mutableStateOf("Enter coordinates (lat,lng)") }

    Column {
        // Geocoding Section
        TextField(
            value = addressInput,
            onValueChange = { addressInput = it },
            label = { Text("Enter a location") }
        )
        Button(onClick = {
            geocodeLocation(context, addressInput) { latLng ->
                geocodeResult = latLng?.let { "Lat: ${it.latitude}, Lng: ${it.longitude}" } ?: "Location not found"
            }
        }) {
            Text("Geocode")
        }
        Text(geocodeResult)

        Spacer(modifier = Modifier.height(16.dp))

        // Reverse Geocoding Section
        TextField(
            value = latLngInput,
            onValueChange = { latLngInput = it },
            label = { Text("Enter coordinates (lat,lng)") }
        )
        Button(onClick = {
            val latLng = parseLatLng(latLngInput)
            if (latLng != null) {
                reverseGeocodeLocation(context, latLng) { address ->
                    reverseGeocodeResult = address ?: "Address not found"
                }
            } else {
                Toast.makeText(context, "Invalid coordinates format", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Reverse Geocode")
        }
        Text(reverseGeocodeResult)
    }
}

@Preview
@Composable
fun AddressGoogleMapsEntryScreenPreview() {
    PriceRecommenderTheme {
        GeocodeExampleScreen()
    }
}