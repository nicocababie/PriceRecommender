package com.example.pricerecommender.ui.screen.cart

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.R
import com.example.pricerecommender.data.model.CartProduct
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.components.NumberInput
import com.example.pricerecommender.ui.screen.components.TextInput
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun CartScreen(
    cart: List<CartProduct>,
    onAddToCart: (String, String, String) -> Unit
) {
    var nameInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var brandInput by remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 18.dp)
    ){
        TextInput(
            title = stringResource(R.string.add_product_name),
            inputState = nameInput,
            saveInput = { nameInput = it })
        NumberInput(
            title = stringResource(R.string.add_product_amount),
            inputState = amountInput,
            saveInput = { amountInput = it }
        )
        TextInput(
            title = stringResource(R.string.add_product_brand),
            inputState = brandInput,
            saveInput = { brandInput = it }
        )
        CustomOutlinedButton(
            text = stringResource(R.string.add_to_cart),
            onClick = { onAddToCart(nameInput, amountInput, brandInput) }
        )
        CartList(cart)
    }
}

@Composable
fun CartList(
    cart: List<CartProduct>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ){
        if (cart.isNotEmpty()){
            Text(
                text = "Cart",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cart) { product ->
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
                        Text(text = "Brand: ${product.brand}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    PriceRecommenderTheme {
        CartScreen(
            listOf(
                CartProduct("Agua", "5", "40"),
                CartProduct("Milanesa", "2", "200"),
            ),
            {name, amount, brand ->}
        )
    }
}