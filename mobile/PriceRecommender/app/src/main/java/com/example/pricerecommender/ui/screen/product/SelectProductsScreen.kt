package com.example.pricerecommender.ui.screen.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.screen.components.Counter
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.components.NumberInput
import com.example.pricerecommender.ui.screen.components.TextInput
import com.example.pricerecommender.ui.screen.purchase.PurchaseUIState
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun SelectProductsScreen(
    productState: ProductUIState,
    purchaseState: PurchaseUIState,
    updateCurrentName: (String) -> Unit,
    updateCurrentAmount: (Int) -> Unit,
    updateCurrentPrice: (Double) -> Unit,
    updateCurrentBrand: (String) -> Unit,
    onAddProductClick: (String, Int, Double, String) -> Unit,
    onReturnToPurchaseClick: () -> Unit,
) {
    val currentName = productState.currentName
    val currentAmount = productState.currentAmount
    val currentPrice = productState.currentPrice
    val currentBrand = productState.currentBrand
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextInput(
                title = stringResource(R.string.add_product_name),
                inputState = currentName,
                saveInput = { updateCurrentName(it) }
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
            Counter(
                currentAmount = currentAmount,
                onDecrementClick = { if (currentAmount != 0) updateCurrentAmount(currentAmount.dec()) },
                onIncrementClick = { updateCurrentAmount(currentAmount.inc()) }
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
                enabled = enabled,
                icon = Icons.Default.Add
            )
            CustomOutlinedButton(
                text = stringResource(R.string.return_to_purchase),
                onClick = onReturnToPurchaseClick,
                icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectProductsScreenPreview() {
    PriceRecommenderTheme {
        SelectProductsScreen(
            ProductUIState(),
            PurchaseUIState(),
            {},
            {},
            {},
            {},
            {name, amount, price, brand ->}, {}
        )
    }
}