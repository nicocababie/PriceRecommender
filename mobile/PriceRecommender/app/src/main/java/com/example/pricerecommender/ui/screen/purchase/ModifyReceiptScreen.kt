package com.example.pricerecommender.ui.screen.purchase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.R
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.ui.ApiUIState
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun ModifyReceiptScreen(
    userId: String,
    purchaseState: PurchaseUIState,
    onItemClick: (Product) -> Unit = {},
    onAddPurchaseClick: () -> Unit =  {},
    onRetry: () -> Unit = {},
    onReturn: () -> Unit = {}
){
    when (purchaseState.apiState) {
        is ApiUIState.Loading -> {
            LoadingScreen()
        }
        is ApiUIState.Error -> {
            val errorState = purchaseState.apiState
            ErrorScreen(
                message = errorState.exception.message ?: errorState.defaultMessage,
                onRetry = onRetry
            )
        }
        is ApiUIState.Success -> {
            val context = LocalContext.current
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxSize()
            ) {
                items(purchaseState.receipt){
                    ReceiptItem(
                        product = it,
                        onItemClick = onItemClick
                    )
                }
                item {
                    CustomOutlinedButton(
                        text = stringResource(R.string.confirm),
                        icon = Icons.Default.Check,
                        modifier = Modifier.padding(top = 12.dp),
                        onClick = {
                            onAddPurchaseClick()
                        }
                    )
                }
                item {
                    CustomOutlinedButton(
                        text = stringResource(R.string.return_to_purchase),
                        onClick = onReturn,
                        icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft
                    )
                }
            }
        }
    }
}


@Composable
fun ReceiptItem(
    product: Product,
    onItemClick: (Product) -> Unit = {}
){
    ElevatedCard(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(product.name)
                    }
                    append(" (${product.brand})")
                    append(" - $${product.price}")
                    append(" - ${product.amount} unit/s")
                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = { onItemClick(product) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModifyReceiptScreenPreview(){
    PriceRecommenderTheme {
        ModifyReceiptScreen(
            userId = "",
            purchaseState = PurchaseUIState(
                receipt = listOf(
                    Product("Agua", 5, 50.0, "Salus", "Disco"),
                    Product("Chocolate", 1, 90.0, "Milka", "Disco")
                ),
            )
        )
    }
}