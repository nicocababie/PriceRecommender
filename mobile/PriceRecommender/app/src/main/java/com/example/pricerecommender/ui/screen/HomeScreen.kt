package com.example.pricerecommender.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun HomeScreen(
    onCheckBestRouteClick: () -> Unit,
    onAddPurchaseClick: () -> Unit,
    onSavingsReportClick: () -> Unit,
    onCartClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.weight(1f)
        ) {
            AddressInput(modifier = Modifier.padding(vertical = 12.dp))

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
                text = stringResource(R.string.savings_report)
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
fun AddressInput(
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { /*TODO*/ },
        modifier = modifier.fillMaxWidth(0.4f)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.address)
        )
        Text(text = stringResource(R.string.address))
    }
}

@Preview(showBackground = true)
@Composable
fun AddressInputPreview() {
    PriceRecommenderTheme {
        AddressInput()
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    PriceRecommenderTheme {
        CustomButton({}, text ="Add Purchase", icon = Icons.Default.AddCircle)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PriceRecommenderTheme {
        HomeScreen({}, {}, {}, {})
    }
}