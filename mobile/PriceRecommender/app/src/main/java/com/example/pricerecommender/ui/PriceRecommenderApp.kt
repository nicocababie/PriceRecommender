package com.example.pricerecommender.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pricerecommender.R
import com.example.pricerecommender.ui.screen.AddAddressScreen
import com.example.pricerecommender.ui.screen.AddPurchaseScreen
import com.example.pricerecommender.ui.screen.AddressManualEntryScreen
import com.example.pricerecommender.ui.screen.CartScreen
import com.example.pricerecommender.ui.screen.HomeScreen
import com.example.pricerecommender.ui.screen.SavingsReportScreen
import com.example.pricerecommender.ui.screen.bestRoute.BestRouteViewModel
import com.example.pricerecommender.ui.screen.bestRoute.CheckBestRouteScreen
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.example.pricerecommender.ui.utils.PriceRecommenderScreen

@Composable
fun PriceRecommenderApp(
    homeScreenViewModel: PriceRecommenderViewModel = hiltViewModel(),
    bestRouteViewModel: BestRouteViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != PriceRecommenderScreen.HomeScreen.name

    val homeScreenState by homeScreenViewModel.uiState.collectAsState()
    val bestRouteState by bestRouteViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PriceRecommenderTopAppBar(
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding -> NavHost(
            navController = navController,
            startDestination = PriceRecommenderScreen.HomeScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = PriceRecommenderScreen.HomeScreen.name) {
                HomeScreen(
                    homeScreenState.currentRange,
                    homeScreenState.currentAddress,
                    homeScreenState.addresses,
                    onAddAddressClick = { navController.navigate(PriceRecommenderScreen.AddAddressScreen.name) },
                    onSelectAddress = { homeScreenViewModel.updateCurrentAddress(it) },
                    onDeleteAddress = { homeScreenViewModel.deleteAddress(it) },
                    onCheckBestRouteClick = { navController.navigate(PriceRecommenderScreen.CheckBestRouteScreen.name) },
                    onAddPurchaseClick = { navController.navigate(PriceRecommenderScreen.AddPurchaseScreen.name) },
                    onSavingsReportClick = { navController.navigate(PriceRecommenderScreen.SavingsReportScreen.name) },
                    onCartClick = { navController.navigate(PriceRecommenderScreen.CartScreen.name) },
                    onRangeSelected = { homeScreenViewModel.updateCurrentRange(it) }
                )
            }

            composable(route = PriceRecommenderScreen.AddAddressScreen.name) {
                AddAddressScreen(
                    onGoogleMapsClick = { navController.navigate(PriceRecommenderScreen.AddressGoogleMapsScreen.name) },
                    onManualEntryClick = { navController.navigate(PriceRecommenderScreen.AddressManualEntryScreen.name) }
                )
            }

            composable(route = PriceRecommenderScreen.CheckBestRouteScreen.name) {
                CheckBestRouteScreen(
                    cameraPosition =  bestRouteViewModel.getCameraPosition(),
                    isMapLoaded = bestRouteState.isMapLoaded,
                    markers = bestRouteViewModel.getMarkers(),
                    updateIsMapLoaded = { bestRouteViewModel.updateIsLoadedMap(it) },
                    updateCameraPosition = { latLng, zoom -> bestRouteViewModel.updateCameraPosition(latLng, zoom) }
                )
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

            composable(route = PriceRecommenderScreen.AddressGoogleMapsScreen.name) {

            }

            composable(route = PriceRecommenderScreen.AddressManualEntryScreen.name) {
                AddressManualEntryScreen(
                    {
                        homeScreenViewModel.insertAddress(it)
                        navController.navigate(PriceRecommenderScreen.HomeScreen.name)
                    },
                    {
                        navController.navigate(PriceRecommenderScreen.HomeScreen.name)
                    })
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceRecommenderTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
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
        PriceRecommenderTopAppBar(false) {}
    }
}