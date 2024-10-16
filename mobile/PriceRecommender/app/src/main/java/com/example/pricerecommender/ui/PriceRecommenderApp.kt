package com.example.pricerecommender.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.screen.AddPurchaseScreen
import com.example.pricerecommender.ui.screen.CartScreen
import com.example.pricerecommender.ui.screen.CheckBestRouteScreen
import com.example.pricerecommender.ui.screen.HomeScreen
import com.example.pricerecommender.ui.screen.SavingsReportScreen
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.example.pricerecommender.ui.utils.PriceRecommenderScreen

@Composable
fun PriceRecommenderApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            PriceRecommenderTopAppBar()
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = PriceRecommenderScreen.HomeScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = PriceRecommenderScreen.HomeScreen.name) {
                HomeScreen(
                    onCheckBestRouteClick = { navController.navigate(PriceRecommenderScreen.CheckBestRouteScreen.name) },
                    onAddPurchaseClick = { navController.navigate(PriceRecommenderScreen.AddPurchaseScreen.name) },
                    onSavingsReportClick = { navController.navigate(PriceRecommenderScreen.SavingsReportScreen.name) },
                    onCartClick = { navController.navigate(PriceRecommenderScreen.CartScreen.name) }
                )
            }

            composable(route = PriceRecommenderScreen.CheckBestRouteScreen.name) {
                CheckBestRouteScreen()
            }

            composable(route = PriceRecommenderScreen.AddPurchaseScreen.name) {
                AddPurchaseScreen()
            }

            composable(route = PriceRecommenderScreen.SavingsReportScreen.name) {
                SavingsReportScreen()
            }

            composable(route = PriceRecommenderScreen.CartScreen.name) {
                CartScreen()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceRecommenderTopAppBar(
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                )

                Image(
                    painter = painterResource(id = R.drawable.pricerecommenderlogo),
                    contentDescription = stringResource(R.string.app_name),
                )
            }
        }
    )
}

@Preview
@Composable
fun PriceRecommenderTopAppBarPreview() {
    PriceRecommenderTheme {
        PriceRecommenderTopAppBar()
    }
}