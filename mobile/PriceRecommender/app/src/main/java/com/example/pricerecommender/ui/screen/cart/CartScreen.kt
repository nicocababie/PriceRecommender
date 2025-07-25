package com.example.pricerecommender.ui.screen.cart

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.pricerecommender.data.model.app.CartProduct
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.ui.ApiUIState
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.example.pricerecommender.ui.screen.components.Counter
import com.example.pricerecommender.ui.screen.components.CustomOutlinedButton
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun CartScreen(
    cartState: CartUIState,
    availableProducts: List<Product>,
    updateCurrentName: (String) -> Unit,
    updateCurrentAmount: (Int) -> Unit,
    updateCurrentBrand: (String) -> Unit,
    onAddProductToCart: (String, Int, String, Context) -> Unit,
    onDeleteProductFromCart: (CartProduct, Context) -> Unit,
    onEmptyCart: (Context) -> Unit,
    emptyState: () -> Unit,
    onRetryClick: () -> Unit
) {
    when (cartState.apiState) {
        is ApiUIState.Loading -> {
            LoadingScreen()
        }

        is ApiUIState.Error -> {
            val errorState = cartState.apiState
            ErrorScreen(
                message = errorState.defaultMessage,
                onRetry = onRetryClick
            )
        }

        is ApiUIState.Success -> {
            val context = LocalContext.current
            var isNameDropdownExpanded by remember { mutableStateOf(false) }
            var isBrandDropdownExpanded by remember { mutableStateOf(false) }
            val currentName = cartState.currentName
            val currentAmount = cartState.currentAmount
            val currentBrand = cartState.currentBrand
            val enabled = currentName != "Name" &&
                    currentAmount != 0 &&
                    currentBrand != "Brand"

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 18.dp)
            ){
                OutlinedButton(
                    onClick = { isNameDropdownExpanded = !isNameDropdownExpanded },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text = currentName
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = stringResource(R.string.add_product_name)
                        )
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenu(
                        expanded = isNameDropdownExpanded,
                        onDismissRequest = { isNameDropdownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp)
                    ) {
                        val productNames = availableProducts.map { it.name }.distinct()
                        val filteredNames = if (currentBrand != "Brand") {
                            availableProducts
                                .filter { it.brand == currentBrand }
                                .map { it.name }
                        } else {
                            productNames
                        }
                        filteredNames.forEach {
                            OutlinedButton(
                                onClick = {
                                    updateCurrentName(it)
                                    isNameDropdownExpanded = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                                border = BorderStroke(0.dp, Color.Transparent)
                            ) {
                                Text(
                                    text = it,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                if (!isNameDropdownExpanded){
                    OutlinedButton(
                        onClick = { isBrandDropdownExpanded = !isBrandDropdownExpanded },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(56.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Text(
                                text = currentBrand
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = stringResource(R.string.add_product_brand)
                            )
                        }
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownMenu(
                            expanded = isBrandDropdownExpanded,
                            onDismissRequest = { isBrandDropdownExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 400.dp)
                        ) {
                            val productBrands = availableProducts.map { it.brand }.distinct()
                            val filteredBrands = if (currentName != "Name") {
                                availableProducts
                                    .filter { it.name == currentName }
                                    .map { it.brand }
                            } else {
                                productBrands
                            }
                            filteredBrands.forEach {
                                OutlinedButton(
                                    onClick = {
                                        updateCurrentBrand(it)
                                        isBrandDropdownExpanded = false
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    border = BorderStroke(0.dp, Color.Transparent)
                                ) {
                                    Text(
                                        text = it,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                    if (!isBrandDropdownExpanded){
                        val cart = cartState.cart
                        Counter(
                            currentAmount = currentAmount,
                            onDecrementClick = { if (currentAmount != 0) updateCurrentAmount(currentAmount.dec()) },
                            onIncrementClick = { updateCurrentAmount(currentAmount.inc()) }
                        )
                        CustomOutlinedButton(
                            text = stringResource(R.string.clear_selection),
                            onClick = emptyState,
                            icon = Icons.Default.Clear
                        )
                        CustomOutlinedButton(
                            text = stringResource(R.string.add_to_cart),
                            enabled = enabled,
                            onClick = {
                                onAddProductToCart(
                                    currentName,
                                    currentAmount,
                                    currentBrand,
                                    context
                                )
                            },
                            icon = Icons.Default.Add
                        )
                        CartList(
                            cart = cart,
                            onEmptyCart = onEmptyCart,
                            onDeleteProductFromCart = onDeleteProductFromCart,
                            context = context,
                            modifier = Modifier.weight(0.5f)
                        )
                    }
                }
            }
        }
    }


}

@Composable
fun CartList(
    cart: List<CartProduct>,
    onEmptyCart: (Context) -> Unit,
    onDeleteProductFromCart: (CartProduct, Context) -> Unit,
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
    onDeleteProductFromCart: (CartProduct, Context) -> Unit,
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
                        product,
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
            CartUIState(apiState = ApiUIState.Success(listOf(
                CartProduct(null,"Agua", 5, "40"),
                CartProduct(null,"Milanesa", 2, "200"),
                CartProduct(null,"Milanesa", 2, "200"),
                CartProduct(null,"Milanesa", 2, "200"),
            ))),
            emptyList(),
            {name ->},
            {amount ->},
            {brand ->},
            {name, amount, brand, context ->},
            {product, context ->},
            {context ->},
            {},
            {}
        )
    }
}