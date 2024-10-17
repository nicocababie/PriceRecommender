package com.example.pricerecommender.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun AddAddressScreen(
    onGoogleMapsClick: () -> Unit,
    onManualEntryClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ){
            CustomButton(
                onClick = onGoogleMapsClick,
                icon = Icons.Default.LocationOn,
                text = "Select from Google Maps"
            )

            CustomButton(
                onClick = onManualEntryClick,
                icon = Icons.Default.Create,
                text = "Manual entry"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAddressScreenPreview() {
    PriceRecommenderTheme {
        AddAddressScreen({}, {})
    }
}