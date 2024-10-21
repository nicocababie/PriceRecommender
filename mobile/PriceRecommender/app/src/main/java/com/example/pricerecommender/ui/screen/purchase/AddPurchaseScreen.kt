package com.example.pricerecommender.ui.screen.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.R
import com.example.pricerecommender.data.model.Product
import com.example.pricerecommender.ui.screen.purchase.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.purchase.components.TextInput
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun AddPurchaseScreen(
    storeName: String,
    storeAddress: String,
    products: List<Product>,
    updateStoreName: (String) -> Unit,
    updateStoreAddress: (String) -> Unit,
    onSelectProductsClick: () -> Unit,
    onAddPurchaseClick: () -> Unit
) {
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
            TextInput(
                title = stringResource(R.string.store_address),
                inputState = storeAddress,
                saveInput = { updateStoreAddress(it) }
            )
            CustomOutlinedButton(
                text = stringResource(R.string.select_products),
                onClick = onSelectProductsClick
            )
        }
        Checkout(products, Modifier.weight(0.5f))
        CustomOutlinedButton(
            text = stringResource(R.string.add_purchase),
            onClick = onAddPurchaseClick
        )
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
            "La Colonial",
            "Osorio 1234",
            listOf(
                Product("Agua", "5", "40", "Salus", "L"),
                Product("Milanesa", "2", "200", "Schneck", "L"),
                Product("Milanesa", "2", "200", "Schneck", "L"),
                Product("Milanesa", "2", "200", "Schneck", "L")
            ), {}, {}, {}, {})
    }
}