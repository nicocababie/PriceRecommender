package com.example.pricerecommender.ui.screen.purchase

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.R
import com.example.pricerecommender.data.model.Product
import com.example.pricerecommender.services.geocodeLocation
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.components.TextInput
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun AddPurchaseScreen(
    userId: String,
    storeName: String,
    storeAddress: String,
    products: List<Product>,
    storeCoord: LatLng,
    updateStoreName: (String) -> Unit,
    updateStoreAddress: (String) -> Unit,
    onSelectProductsClick: () -> Unit,
    onAddPurchaseClick: (String, String, String, List<Product>, LatLng, Context) -> Unit,
    updateStoreCoord: (LatLng) -> Unit,
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextInput(
                title = stringResource(R.string.store_name),
                inputState = storeName,
                saveInput = { updateStoreName(it) }
            )
            Box(
                contentAlignment = Alignment.CenterEnd
            ){
                TextInput(
                    title = stringResource(R.string.store_address),
                    inputState = storeAddress,
                    saveInput = { updateStoreAddress(it) }
                )
                OutlinedButton(
                    modifier = Modifier.padding(top = 22.dp),
                    border = BorderStroke(0.dp, Color.Transparent),
                    onClick = {
                    geocodeLocation(context, storeAddress) { latLng ->
                        latLng?.let {
                            val coord = LatLng(it.latitude, it.longitude)
                            updateStoreCoord(coord)
                            Toast.makeText(
                                context,
                                "Address checked successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } ?: Toast.makeText(
                            context,
                            "Invalid address format",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
            CustomOutlinedButton(
                text = stringResource(R.string.select_products),
                onClick = onSelectProductsClick
            )
        }
        Checkout(products, Modifier.weight(0.5f))
        if (storeName != "" && storeAddress != ""){
            CustomOutlinedButton(
                text = stringResource(R.string.add_purchase),
                onClick = {
                    onAddPurchaseClick(
                        userId,
                        storeName,
                        storeAddress,
                        products,
                        storeCoord,
                        context
                    )
                }
            )
        }
    }
}

@Composable
fun Checkout(
    products: List<Product>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ){
        if (products.isNotEmpty()){
            Text(
                text = "Checkout",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Product: ${product.name}", fontWeight = FontWeight.Bold)
                        Text(text = "Amount: ${product.amount}")
                        Text(text = "Price: ${product.price}")
                        Text(text = "Brand: ${product.brand}")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddPurchaseScreenPreview() {
    PriceRecommenderTheme {
        AddPurchaseScreen(
            "",
            "La Colonial",
            "Osorio 1234",
            listOf(
                Product("Agua", 5, 40.0, "Salus", "L"),
                Product("Milanesa", 5, 40.0, "Schneck", "L"),
                Product("Milanesa", 5, 40.0, "Schneck", "L"),
                Product("Milanesa", 5, 40.0, "Schneck", "L")
            ),
            LatLng(0.0,0.0),
            {},
            {},
            {},
            { userId, storeName, storeAddress, products, coord, context ->},
            {},
        )
    }
}