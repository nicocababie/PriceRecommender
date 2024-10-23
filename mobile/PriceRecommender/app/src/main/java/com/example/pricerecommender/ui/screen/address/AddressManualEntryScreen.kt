package com.example.pricerecommender.ui.screen.address

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
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
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AddressManualEntryScreen(
    currentAddress: String,
    currentDepartment: String,
    currentCoord: LatLng,
    updateCurrentAddress: (String) -> Unit,
    updateCurrentDepartment: (String) -> Unit,
    updateCurrentCoord: (LatLng) -> Unit,
    cameraPosition: CameraPositionState,
    departments: List<String>,
    onAddAddressClick: (String, Double, Double) -> Unit,
    onCancelClick: () -> Unit,
    emptyState: () -> Unit,
    updateCameraPosition: (LatLng, Float) -> Unit
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var expanded by remember { mutableStateOf(false) }

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
                value = currentAddress,
                onValueChange = { updateCurrentAddress(it) },
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = currentDepartment,
                )
                OutlinedButton(
                    onClick = { expanded = !expanded },
                    border = BorderStroke(0.dp, Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Select department")
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    departments.forEach {
                        OutlinedButton(
                            onClick = {
                                updateCurrentDepartment(it)
                                expanded = false
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            border = BorderStroke(0.dp, Color.Transparent)
                        ) {
                            Text(text = it)
                        }
                    }
                }
            }

            if (!expanded){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = {
                        keyboardController?.hide()
                        val input = "${currentAddress}, ${currentDepartment}"
                        geocodeLocation(context, input) { latLng ->
                            latLng?.let {
                                val coord = LatLng(it.latitude, it.longitude)
                                updateCurrentCoord(coord)
                                updateCameraPosition(coord, 15f)
                                cameraPosition.position =
                                    CameraPosition.fromLatLngZoom(currentCoord, 15f)
                            } ?: Toast.makeText(
                                context,
                                "Invalid address format",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Text(text = "Check")
                    }
                    Button(onClick =
                    {
                        onCancelClick()
                        emptyState()
                    }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
            if (currentCoord != LatLng(0.0, 0.0)) {
                val markerState = MarkerState(position = currentCoord)
                GoogleMap(
                    cameraPositionState = cameraPosition,
                    modifier = Modifier
                        .fillMaxSize(0.9f)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Marker(state = markerState)
                }
                CustomOutlinedButton(
                    text = "Submit",
                    onClick = {
                        onAddAddressClick(
                            currentAddress,
                            currentCoord.latitude,
                            currentCoord.longitude
                        )
                        emptyState()
                    }
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAddressManualScreenPreview() {
    PriceRecommenderTheme {
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10f)
        }
        AddressManualEntryScreen(
            "",
            "Select a department",
            LatLng(0.0,0.0),
            {},
            {},
            {},
            cameraPositionState,listOf("Colonia", "Rocha"),
            { address, lat, lng ->},
            {},
            {},
            {latLng, zoom ->})
    }
}