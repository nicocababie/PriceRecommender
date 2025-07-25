package com.example.pricerecommender.ui.screen.purchasesReport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.data.model.app.PurchaseData
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.ui.ApiUIState
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun PurchasesReportScreen(
    uiState: PurchaseReportUIState,
    onPurchaseClick: (PurchaseData) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState.apiState) {
        is ApiUIState.Loading -> {
            LoadingScreen()
        }
        is ApiUIState.Error -> {
            val errorState = uiState.apiState
            ErrorScreen(
                message = errorState.defaultMessage,
                onRetry = onRetryClick
            )
        }
        is ApiUIState.Success -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Your last 5 purchases are:",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                items(uiState.report) { purchase ->
                    PurchaseItem(
                        purchase,
                        onPurchaseClick = onPurchaseClick
                    )
                }
            }
        }
    }
}


@Composable
fun PurchaseItem(
    purchase: PurchaseData,
    onPurchaseClick: (PurchaseData) -> Unit
) {
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
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.9f)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.Bold)
                        ) {
                            append(purchase.storeName)
                        }
                        append(" (${purchase.storeAddress})")
                    }
                )
                Text(
                    text = purchase.date,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            IconButton(
                onClick = { onPurchaseClick(purchase) }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
fun PurchaseItemPreview() {
    PriceRecommenderTheme {
        PurchaseItem(
            PurchaseData(
            storeName = "Tienda Inglesa",
            storeAddress = "Montevideo Shopping",
            date = "27/10/2024",
            products = listOf(
                Product(
                    name = "Yerba",
                    amount = 2,
                    brand = "Canarias",
                    price = 200.0,
                    store = ""
                ),
                Product(
                    name = "Agua",
                    amount = 5,
                    brand = "Salus",
                    price = 50.0,
                    store = ""
                )
            )
        ),
            {})
    }
}

@Preview(showBackground = true)
@Composable
fun PurchaseReportScreenPreview() {
    PriceRecommenderTheme {
        PurchasesReportScreen(
            uiState = PurchaseReportUIState(),
            {},
            {})
    }
}