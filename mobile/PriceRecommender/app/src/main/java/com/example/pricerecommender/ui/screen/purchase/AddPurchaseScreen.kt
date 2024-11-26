package com.example.pricerecommender.ui.screen.purchase

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.R
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.ui.ApiUIState
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.components.TextInput
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun AddPurchaseScreen(
    purchaseState: PurchaseUIState,
    userId: String,
    updateStoreName: (String) -> Unit,
    onSelectStoreAddressClick: () -> Unit,
    onSelectProductsClick: () -> Unit,
    onAddPurchaseClick: (String, String, String, List<Product>, LatLng, Context) -> Unit,
    onCameraClick: () -> Unit,
    onRetry: () -> Unit
) {
    val context = LocalContext.current
    val storeName = purchaseState.purchase.name
    val storeAddress = purchaseState.purchase.address
    val products = purchaseState.purchase.products
    val storeCoord = purchaseState.storeCoord
    val enabled = storeName != "" && storeAddress != ""

    when(purchaseState.apiState) {
        is ApiUIState.Loading -> {
            LoadingScreen()
        }
        is ApiUIState.Error -> {
            val errorState = purchaseState.apiState
            ErrorScreen(
                message = errorState.defaultMessage,
                onRetry = { onRetry() }
            )
        }
        is ApiUIState.Success -> {
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
                    if (storeAddress == ""){
                        CustomOutlinedButton(
                            text = stringResource(R.string.select_store_address),
                            onClick = onSelectStoreAddressClick,
                            icon = Icons.Default.Place
                        )
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        ) {
                            Text(
                                text = "Address: $storeAddress",
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = onSelectStoreAddressClick
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    OutlinedButton(
                        onClick = onCameraClick,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(56.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.receipt_capture),
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(R.drawable.camera),
                                contentDescription = stringResource(R.string.receipt_capture)
                            )
                        }
                    }
                    CustomOutlinedButton(
                        text = stringResource(R.string.select_products),
                        onClick = onSelectProductsClick,
                        icon = Icons.Default.Menu
                    )
                }
                Checkout(products, Modifier.weight(0.5f))
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
                    },
                    enabled = enabled,
                    icon = Icons.Default.Add
                )
            }
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
            PurchaseUIState(),
            "",
            {},
            {},
            {},
            { userId, storeName, storeAddress, products, coord, context ->},
            {},
            {}
        )
    }
}