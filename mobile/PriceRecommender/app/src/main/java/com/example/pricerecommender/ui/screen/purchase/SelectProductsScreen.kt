package com.example.pricerecommender.ui.screen.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    currentName: String,
    currentAmount: Int,
    currentPrice: Double,
    currentBrand: String,
    updateCurrentName: (String) -> Unit,
    updateCurrentAmount: (Int) -> Unit,
    updateCurrentPrice: (Double) -> Unit,
    updateCurrentBrand: (String) -> Unit,
    onAddProductClick: (String, Int, Double, String) -> Unit,
    onReturnToPurchaseClick: () -> Unit
) {
    val enabled =
            currentName != "" &&
            currentAmount != 0 &&
            currentPrice != 0.0 &&
            currentBrand != ""

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
                inputState = currentName,
                saveInput = { updateCurrentName(it) }
            )
            NumberInput(
                title = stringResource(R.string.add_product_amount),
                inputState = currentAmount.toString(),
                saveInput = { input ->
                    val productAmount = if (input.isNotEmpty()) input.toInt() else 0
                    updateCurrentAmount(productAmount)
                }
            )
            NumberInput(
                title = stringResource(R.string.add_product_price),
                inputState = currentPrice.toString(),
                saveInput = { input ->
                    val productPrice = if (input.isNotEmpty()) input.toDouble() else 0.0
                    updateCurrentPrice(productPrice)
                }
            )
            TextInput(
                title = stringResource(R.string.add_product_brand),
                inputState = currentBrand,
                saveInput = { updateCurrentBrand(it) }
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            CustomOutlinedButton(
                text = stringResource(R.string.add_product),
                onClick = {
                    onAddProductClick(
                        currentName,
                        currentAmount,
                        currentPrice,
                        currentBrand
                    )
                },
                enabled = enabled
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
        SelectProductsScreen(
            "Cereales",
            2,
            80.0,
            "Kellogs",
            {},
            {},
            {},
            {},
            {name, amount, price, brand ->}, {}
        )
    }
}