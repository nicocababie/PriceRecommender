package com.example.pricerecommender.ui.screen.purchasesReport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.PurchaseData
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun SelectedPurchaseScreen(purchase: PurchaseData) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
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
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(purchase.products) {
                ElevatedCard(
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                ){
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold)
                            ) {
                                append(it.name)
                            }
                            append(" (${it.brand}) ")
                            append("x${it.amount}")
                        })
                        Text(text = "$${it.price}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedPurchaseScreenPreview() {
    PriceRecommenderTheme {
        SelectedPurchaseScreen(PurchaseData(
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
        ))
    }
}