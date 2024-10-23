package com.example.pricerecommender.ui.screen.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.components.NumberInput
import com.example.pricerecommender.ui.screen.components.TextInput
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun SelectProductsScreen(
    onAddProductClick: (String, Int, Double, String) -> Unit,
    onReturnToPurchaseClick: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productAmount by remember { mutableStateOf(0) }
    var productPrice by remember { mutableStateOf(0.0) }
    var productBrand by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextInput(
                title = stringResource(R.string.add_product_name),
                inputState = productName,
                saveInput = { productName = it }
            )
            NumberInput(
                title = stringResource(R.string.add_product_amount),
                inputState = productAmount.toString(),
                saveInput = { input ->
                    productAmount = if (input.isNotEmpty()) input.toInt() else 0
                }
            )
            NumberInput(
                title = stringResource(R.string.add_product_price),
                inputState = productPrice.toString(),
                saveInput = { input ->
                    productPrice = if (input.isNotEmpty()) input.toDouble() else 0.0
                }
            )
            TextInput(
                title = stringResource(R.string.add_product_brand),
                inputState = productBrand,
                saveInput = { productBrand = it }
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            CustomOutlinedButton(
                text = stringResource(R.string.add_product),
                onClick = {
                    onAddProductClick(
                        productName,
                        productAmount,
                        productPrice,
                        productBrand
                    )
                }
            )
            CustomOutlinedButton(
                text = stringResource(R.string.return_to_purchase),
                onClick = onReturnToPurchaseClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectProductsScreenPreview() {
    PriceRecommenderTheme {
        SelectProductsScreen({name, amount, price, brand ->}, {})
    }
}