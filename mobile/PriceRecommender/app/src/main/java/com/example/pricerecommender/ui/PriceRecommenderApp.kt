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
import com.example.pricerecommender.ui.screen.SavingsReportScreen
import com.example.pricerecommender.ui.screen.address.AddressManualEntryScreen
import com.example.pricerecommender.ui.screen.address.AddressViewModel
import com.example.pricerecommender.ui.screen.api.ErrorScreen
import com.example.pricerecommender.ui.screen.api.LoadingScreen
import com.example.pricerecommender.ui.screen.bestRoute.BestRouteViewModel
import com.example.pricerecommender.ui.screen.bestRoute.CheckBestRouteScreen
import com.example.pricerecommender.ui.screen.cart.CartScreen
import com.example.pricerecommender.ui.screen.cart.CartViewModel
import com.example.pricerecommender.ui.screen.home.HomeScreen
import com.example.pricerecommender.ui.screen.home.HomeViewModel
import com.example.pricerecommender.ui.screen.purchase.AddPurchaseScreen
import com.example.pricerecommender.ui.screen.purchase.PurchaseViewModel
import com.example.pricerecommender.ui.screen.purchase.SelectProductsScreen
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.example.pricerecommender.ui.utils.PriceRecommenderScreen

@Composable
fun PriceRecommenderApp(
    homeViewModel: HomeViewModel = hiltViewModel(),
    bestRouteViewModel: BestRouteViewModel = hiltViewModel(),
    purchaseViewModel: PurchaseViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = PriceRecommenderScreen.valueOf(
        backStackEntry?.destination?.route ?: PriceRecommenderScreen.HomeScreen.name
    )
    val canNavigateBack = backStackEntry?.destination?.route != PriceRecommenderScreen.HomeScreen.name

    val homeState by homeViewModel.uiState.collectAsState()
    val bestRouteState by bestRouteViewModel.uiState.collectAsState()
    val purchaseState by purchaseViewModel.uiState.collectAsState()
    val cartState by cartViewModel.uiState.collectAsState()
    val addressState by addressViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PriceRecommenderTopAppBar(
                canNavigateBack = canNavigateBack,
                currentScreen = currentScreen,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding -> NavHost(
            navController = navController,
            startDestination = PriceRecommenderScreen.HomeScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = PriceRecommenderScreen.HomeScreen.name) {
                when (homeState.apiState) {
                    is ApiUIState.Loading -> {
                        LoadingScreen()
                    }
                    is ApiUIState.Error -> {
                        val errorState = homeState.apiState as ApiUIState.Error
                        ErrorScreen(
                            message = errorState.exception.message ?: errorState.defaultMessage,
                            onRetry = { homeViewModel.getCurrentUserId() }
                        )
                    }
                    is ApiUIState.Success -> {
                        HomeScreen(
                            cartState.cart.size,
                            homeState.currentRange,
                            homeState.currentAddress,
                            homeState.addresses,
                            onAddAddressClick = { navController.navigate(PriceRecommenderScreen.AddressManualEntryScreen.name) },
                            onSelectAddress = { homeViewModel.updateCurrentAddress(it) },
                            onDeleteAddress = { homeViewModel.deleteAddress(it) },
                            onCheckBestRouteClick = { navController.navigate(PriceRecommenderScreen.CheckBestRouteScreen.name) },
                            onAddPurchaseClick = { navController.navigate(PriceRecommenderScreen.AddPurchaseScreen.name) },
                            onSavingsReportClick = { navController.navigate(PriceRecommenderScreen.SavingsReportScreen.name) },
                            onCartClick = { navController.navigate(PriceRecommenderScreen.CartScreen.name) },
                            onRangeSelected = { homeViewModel.updateCurrentRange(it) }
                        )
                    }
                }
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
                AddPurchaseScreen(
                    storeName = purchaseState.purchase.name,
                    storeAddress = purchaseState.purchase.address,
                    products = purchaseState.purchase.products,
                    updateStoreName = { purchaseViewModel.updateStoreName(it) },
                    updateStoreAddress = { purchaseViewModel.updateStoreAddress(it) },
                    onSelectProductsClick = { navController.navigate(PriceRecommenderScreen.SelectProductsScreen.name) },
                    onAddPurchaseClick = { navController.popBackStack() }
                )
            }

            composable(route = PriceRecommenderScreen.SelectProductsScreen.name) {
                SelectProductsScreen(
                    onAddProductClick = { name, amount, price, brand ->
                        purchaseViewModel.updateProductsList(name, amount, price, brand)
                        navController.popBackStack()
                    },
                    onReturnToPurchaseClick = { navController.popBackStack() }
                )
            }

            composable(route = PriceRecommenderScreen.SavingsReportScreen.name) {
                SavingsReportScreen()
            }

            composable(route = PriceRecommenderScreen.CartScreen.name) {
                CartScreen(
                    cart = cartState.cart,
                    onAddToCart = { name, amount, brand ->
                        cartViewModel.updateCart(name, amount, brand)
                    }
                )
            }

            composable(route = PriceRecommenderScreen.AddressManualEntryScreen.name) {
                AddressManualEntryScreen(
                    currentAddress = addressState.currentAddress,
                    currentDepartment = addressState.currentDepartment,
                    currentCoord = addressState.currentCoord,
                    updateCurrentAddress = { addressViewModel.updateCurrentAddress(it) },
                    updateCurrentDepartment = { addressViewModel.updateCurrentDepartment(it) },
                    updateCurrentCoord = { addressViewModel.updateCurrentCoord(it) },
                    cameraPosition = addressViewModel.getCameraPosition(),
                    departments = addressState.departments,
                    onAddAddressClick = { address, lat, lng ->
                        homeViewModel.insertAddress(address, lat, lng)
                        navController.popBackStack(PriceRecommenderScreen.HomeScreen.name, false)
                    },
                    onCancelClick = {
                        navController.popBackStack(PriceRecommenderScreen.HomeScreen.name, false)
                    },
                    emptyState = { addressViewModel.emptyState() },
                    updateCameraPosition = { latLng, zoom ->
                        addressViewModel.updateCameraPosition(latLng, zoom) }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceRecommenderTopAppBar(
    canNavigateBack: Boolean,
    currentScreen: PriceRecommenderScreen,
    navigateUp: () -> Unit
) {
    val title = when(currentScreen.name) {
        PriceRecommenderScreen.HomeScreen.name -> R.string.app_name
        else -> currentScreen.title
    }
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
                    text = stringResource(title),
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
        PriceRecommenderTopAppBar(false, PriceRecommenderScreen.HomeScreen) {}
    }
}