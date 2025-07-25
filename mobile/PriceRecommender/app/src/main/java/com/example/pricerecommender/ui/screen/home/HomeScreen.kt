package com.example.pricerecommender.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pricerecommender.R
import com.example.pricerecommender.data.model.app.AddressDetail
import com.example.pricerecommender.ui.ApiUIState
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.example.pricerecommender.ui.screen.cart.CartUIState
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun HomeScreen(
    homeState: HomeUIState,
    cartState: CartUIState,
    onAddAddressClick: () -> Unit,
    onSelectAddress: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onCheckBestRouteClick: () -> Unit,
    onAddPurchaseClick: () -> Unit,
    onSavingsReportClick: () -> Unit,
    onCartClick: () -> Unit,
    onRangeSelected: (Float) -> Unit,
    onRetryClick: () -> Unit
) {
    when (homeState.apiState) {
        is ApiUIState.Loading -> {
            LoadingScreen()
        }
        is ApiUIState.Error -> {
            val errorState = homeState.apiState
            ErrorScreen(
                message = errorState.exception.message ?: errorState.defaultMessage,
                onRetry = onRetryClick
            )
        }
        is ApiUIState.Success -> {
            val cartItems = cartState.cart.size
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                RangeInput(
                    homeState.currentRange,
                    onRangeSelected
                )

                AddressInput(
                    currentAddress = homeState.currentAddress.address,
                    addressDetails = homeState.addresses,
                    onAddAddressClick= onAddAddressClick,
                    onSelectAddress = onSelectAddress,
                    onDeleteAddress = onDeleteAddress,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 24.dp))

                    CustomButton(
                        onClick = onCheckBestRouteClick,
                        icon = Icons.Default.CheckCircle,
                        text = stringResource(R.string.check_the_best_route),
                    )

                    CustomButton(
                        onClick = onAddPurchaseClick,
                        icon = Icons.Default.AddCircle,
                        text = stringResource(R.string.add_purchase)
                    )

                    CustomButton(
                        onClick = onSavingsReportClick,
                        icon = Icons.Default.Info,
                        text = stringResource(R.string.purchases_report)
                    )
                }
                Button(
                    onClick = onCartClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(topStart = 160.dp, topEnd = 160.dp)
                        ),
                    shape = RoundedCornerShape(topStart = 160.dp, topEnd = 160.dp)
                ) {
                    when (cartState.apiState) {
                        is ApiUIState.Loading -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.surface
                            )
                        }
                        is ApiUIState.Error -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ){
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "",
                                    modifier = Modifier.fillMaxSize(0.4f)
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(fontWeight = FontWeight.ExtraBold)
                                        ) {
                                            append(stringResource(R.string.server_error))
                                        }
                                        append(": ${stringResource(R.string.click_to_see_more_details)}")
                                    },
                                    fontSize = 16.sp
                                )
                            }
                        }
                        is ApiUIState.Success -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = stringResource(R.string.create_shopping_list),
                                        modifier = Modifier
                                            .padding(top = 16.dp)
                                            .size(60.dp)
                                    )
                                    Text(
                                        text = stringResource(R.string.create_shopping_list)
                                    )
                                    if (cartItems > 0) {
                                        Text(text = "(${cartItems})")
                                    }
                                }
                            }
                            
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    icon: ImageVector,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(0.5f)
        ) {
            Icon(imageVector = icon, contentDescription = text)
            Text(text = text)
        }
    }
}

@Composable
fun RangeInput(
    currentRange: Float,
    onRangeSelected: (Float) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "${stringResource(R.string.range)} (${currentRange.toInt()} km)"
        )

        Slider(
            value = currentRange,
            onValueChange = { onRangeSelected(it) },
            valueRange = 0f..30f,
            modifier = Modifier.width(200.dp)
        )
    }
}

@Composable
fun OutlinedTransparentButton(
    text: Int,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(0.4f)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(text)
        )
        Text(text = stringResource(text))
    }
}

@Composable
fun AddressInput(
    currentAddress: String,
    addressDetails: List<AddressDetail>,
    onAddAddressClick: () -> Unit,
    onSelectAddress: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (addressDetails.isEmpty()){
        OutlinedTransparentButton(
            R.string.address,
            Icons.Default.Add,
            onClick = onAddAddressClick,
            modifier = modifier
        )
    } else {
        AddressesList(currentAddress, addressDetails, onSelectAddress, onDeleteAddress, onAddAddressClick)
    }
}

@Composable
fun AddressesList(
    currentAddress: String,
    addressDetails: List<AddressDetail>,
    onSelectAddress: (String) -> Unit,
    onDeleteAddress: (String) -> Unit,
    onAddAddressClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = currentAddress,
            )
            OutlinedButton(
                onClick = { expanded = true },
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.show_addresses)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                addressDetails.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = it.address,
                            modifier = Modifier
                                .padding(start = 24.dp)
                                .weight(1f)
                                .clickable {
                                    onSelectAddress(it.address)
                                    expanded = false
                                }
                        )
                        OutlinedButton(
                            onClick = {
                                onDeleteAddress(it.address)
                                expanded = false
                            },
                            border = BorderStroke(0.dp, Color.Transparent)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Delete",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        border = BorderStroke(0.dp, Color.Transparent),
                        onClick = onAddAddressClick,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.address)
                        )
                        Text(text = stringResource(R.string.address))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PriceRecommenderTheme {
        HomeScreen(
            HomeUIState(apiState = ApiUIState.Success("")),
            CartUIState(apiState = ApiUIState.Error(exception = Throwable())),
            {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}