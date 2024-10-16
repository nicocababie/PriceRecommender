package com.example.pricerecommender.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme

@Composable
fun HomeScreen(
    onCheckBestRouteClick: () -> Unit,
    onAddPurchaseClick: () -> Unit,
    onSavingsReportClick: () -> Unit,
    onCartClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (
            cartButtonRef,
            bestRouteButtonRef,
            addPurchaseButtonRef,
            savingsReportButtonRef
        ) = createRefs()

        CustomButton(
            onClick = onCheckBestRouteClick,
            icon = Icons.Default.CheckCircle,
            text = stringResource(R.string.check_the_best_route),
            modifier = Modifier.constrainAs(bestRouteButtonRef) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        CustomButton(
            onClick = onAddPurchaseClick,
            icon = Icons.Default.AddCircle,
            text = stringResource(R.string.add_purchase),
            modifier = Modifier.constrainAs(addPurchaseButtonRef) {
                top.linkTo(bestRouteButtonRef.bottom, 8.dp)
                start.linkTo(bestRouteButtonRef.start)
                end.linkTo(bestRouteButtonRef.end)
                width = Dimension.fillToConstraints
            }
        )

        CustomButton(
            onClick = onSavingsReportClick,
            icon = Icons.Default.Info,
            text = stringResource(R.string.savings_report),
            modifier = Modifier.constrainAs(savingsReportButtonRef) {
                top.linkTo(addPurchaseButtonRef.bottom, 8.dp)
                start.linkTo(bestRouteButtonRef.start)
                end.linkTo(bestRouteButtonRef.end)
                width = Dimension.fillToConstraints
            }
        )

        Button(
            onClick = onCartClick,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(160.dp)
                )
                .constrainAs(cartButtonRef) {
                    top.linkTo(savingsReportButtonRef.bottom, 380.dp)
                }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter // Alinea la columna en la parte superior
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
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = text)
            Text(text = text)
        }
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