package com.example.pricerecommender.ui.screen.address

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.services.geocodeLocation
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AddressManualEntryScreen(
    onAddAddressClick: (String, Double, Double) -> Unit,
    onCancelClick: () -> Unit
) {
    val context = LocalContext.current
    var addressInput by remember { mutableStateOf("")}
    var coord by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var lat by remember { mutableStateOf(0.0) }
    var lng by remember { mutableStateOf(0.0) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coord, 10f)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Text(
                text = "Enter an address",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            OutlinedTextField(
                value = addressInput,
                onValueChange = { addressInput = it },
                singleLine = true,
                label = { Text("Address") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = {
                    geocodeLocation(context, addressInput) { latLng ->
                        latLng?.let {
                            lat = it.latitude
                            lng = it.longitude
                            coord = LatLng(lat, lng)
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(coord, 15f)
                        } ?:
                        Toast.makeText(context, "Invalid address format", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Check")
                }
                Button(onClick = onCancelClick) {
                    Text(text = "Cancel")
                }
            }
            if (coord != LatLng(0.0, 0.0)) {
                val markerState = MarkerState(position = coord)
                GoogleMap(
                    cameraPositionState = cameraPositionState,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Marker(state = markerState)
                }
                CustomOutlinedButton(
                    text = "Submit",
                    onClick = { onAddAddressClick(addressInput, lat, lng) }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAddressManualScreenPreview() {
    PriceRecommenderTheme {
        AddressManualEntryScreen({address, lat, lng ->}, {})
    }
}