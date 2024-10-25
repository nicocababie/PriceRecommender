package com.example.pricerecommender.ui.screen.cart

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
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
import com.example.pricerecommender.data.model.CartProduct
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.screen.components.NumberInput
import com.example.pricerecommender.ui.screen.components.TextInput
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun CartScreen(
    cart: List<CartProduct>,
    currentName: String,
    currentAmount: Int,
    currentBrand: String,
    updateCurrentName: (String) -> Unit,
    updateCurrentAmount: (Int) -> Unit,
    updateCurrentBrand: (String) -> Unit,
    onAddProductToCart: (String, Int, String, Context) -> Unit,
    onDeleteProductFromCart: (String, Int, String, Context) -> Unit,
    onEmptyCart: (Context) -> Unit
) {
    val context = LocalContext.current
    val enabled = currentName != "" &&
            currentAmount != 0 &&
            currentBrand != ""

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 18.dp)
    ){
        TextInput(
            title = stringResource(R.string.add_product_name),
            inputState = currentName,
            saveInput = { updateCurrentName(it) })
        NumberInput(
            title = stringResource(R.string.add_product_amount),
            inputState = currentAmount.toString(),
            saveInput = { input ->
                val productAmount = if (input.isNotEmpty()) input.toInt() else 0
                updateCurrentAmount(productAmount)
            }
        )
        TextInput(
            title = stringResource(R.string.add_product_brand),
            inputState = currentBrand,
            saveInput = { updateCurrentBrand(it) }
        )
        CustomOutlinedButton(
            text = stringResource(R.string.add_to_cart),
            enabled = enabled,
            onClick = { onAddProductToCart(currentName, currentAmount, currentBrand, context) }
        )
        CartList(
            cart = cart,
            onEmptyCart = onEmptyCart,
            onDeleteProductFromCart = onDeleteProductFromCart,
            context = context,
            modifier = Modifier.weight(0.5f)
        )
        CustomOutlinedButton(
            text = "Return to home",
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun CartList(
    cart: List<CartProduct>,
    onEmptyCart: (Context) -> Unit,
    onDeleteProductFromCart: (String, Int, String, Context) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(16.dp)
    ){
        if (cart.isNotEmpty()){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = stringResource(R.string.cart),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                OutlinedIconButton(
                    onClick = { onEmptyCart(context) },
                    border = BorderStroke(0.dp, Color.Transparent),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete)
                    )
                }
            }
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cart) {
                CartItem(
                    product = it,
                    onDeleteProductFromCart = onDeleteProductFromCart,
                    context = context
                )
            }
        }
    }
}

@Composable
fun CartItem(
    product: CartProduct,
    onDeleteProductFromCart: (String, Int, String, Context) -> Unit,
    context: Context,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(text = "Product: ${product.name}", fontWeight = FontWeight.Bold)
                Text(text = "Amount: ${product.amount}")
                Text(text = "Brand: ${product.brand}")
            }
            OutlinedIconButton(
                onClick = {
                    onDeleteProductFromCart(
                        product.name,
                        product.amount,
                        product.brand,
                        context
                    )
                          },
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = stringResource(R.string.empty)
                )
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
                CartProduct("Agua", 5, "40"),
                CartProduct("Milanesa", 2, "200"),
                CartProduct("Milanesa", 2, "200"),
                CartProduct("Milanesa", 2, "200"),
            ),
            "",
            1,
            "Elite",
            {name ->},
            {amount ->},
            {brand ->},
            {name, amount, brand, context ->},
            {name, amount, brand, context ->},
            {context ->}
        )
    }
}