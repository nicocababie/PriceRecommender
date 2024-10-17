package com.example.pricerecommender.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun AddressManualEntryScreen(
    onAddAddressClick: (String) -> Unit,
    onCancelClick: () -> Unit
) {
    var addressInput by remember { mutableStateOf("")}
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
                onValueChange = { addressInput = it }
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(onClick = { onAddAddressClick(addressInput) }) {
                    Text(text = "Submit")
                }
                Button(onClick = onCancelClick) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAddressManualScreenPreview() {
    PriceRecommenderTheme {
        AddressManualEntryScreen({}, {})
    }
}