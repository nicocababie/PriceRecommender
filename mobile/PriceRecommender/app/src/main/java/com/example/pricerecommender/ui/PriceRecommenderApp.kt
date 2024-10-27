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
import com.example.pricerecommender.ui.screen.product.ProductViewModel
import com.example.pricerecommender.ui.screen.product.SelectProductsScreen
import com.example.pricerecommender.ui.screen.purchase.AddPurchaseScreen
import com.example.pricerecommender.ui.screen.purchase.PurchaseViewModel
import com.example.pricerecommender.ui.screen.purchasesReport.PurchaseReportViewModel
import com.example.pricerecommender.ui.screen.purchasesReport.PurchasesReportScreen
import com.example.pricerecommender.ui.screen.purchasesReport.SelectedPurchaseScreen
import com.example.pricerecommender.ui.theme.PriceRecommenderTheme
import com.example.pricerecommender.ui.utils.PriceRecommenderScreen

@Composable
fun PriceRecommenderApp(
    homeViewModel: HomeViewModel = hiltViewModel(),
    bestRouteViewModel: BestRouteViewModel = hiltViewModel(),
    purchaseViewModel: PurchaseViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    addressViewModel: AddressViewModel = hiltViewModel(),
    purchaseReportViewModel: PurchaseReportViewModel = hiltViewModel(),
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
    val productState by productViewModel.uiState.collectAsState()
    val cartState by cartViewModel.uiState.collectAsState()
    val addressState by addressViewModel.uiState.collectAsState()
    val purchaseReportState by purchaseReportViewModel.uiState.collectAsState()

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
                HomeScreen(
                    homeState,
                    cartState,
                    onAddAddressClick = { navController.navigate(PriceRecommenderScreen.AddressManualEntryScreen.name) },
                    onSelectAddress = { homeViewModel.updateCurrentAddress(it) },
                    onDeleteAddress = { homeViewModel.deleteAddress(it) },
                    onCheckBestRouteClick = { navController.navigate(PriceRecommenderScreen.CheckBestRouteScreen.name) },
                    onAddPurchaseClick = { navController.navigate(PriceRecommenderScreen.AddPurchaseScreen.name) },
                    onSavingsReportClick = {
                        purchaseReportViewModel.getReport(purchaseReportState.userId)
                        navController.navigate(PriceRecommenderScreen.PurchasesReportScreen.name)
                    },
                    onCartClick = { navController.navigate(PriceRecommenderScreen.CartScreen.name) },
                    onRangeSelected = { homeViewModel.updateCurrentRange(it) },
                    onRetryClick = { homeViewModel.getCurrentUserId() }
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
                AddPurchaseScreen(
                    purchaseState = purchaseState,
                    userId = homeState.userId,
                    updateStoreName = { purchaseViewModel.updateStoreName(it) },
                    onSelectStoreAddressClick = { navController.navigate(PriceRecommenderScreen.StoreAddressScreen.name) },
                    onSelectProductsClick = { navController.navigate(PriceRecommenderScreen.SelectProductsScreen.name) },
                    onAddPurchaseClick = { userId, storeName, storeAddress, products, coord, context ->
                        purchaseViewModel.addPurchase(userId, storeName, storeAddress, products, coord, context)
                        navController.popBackStack()
                    }
                )
            }

        composable(route = PriceRecommenderScreen.StoreAddressScreen.name) {
            AddressManualEntryScreen(
                currentAddress = purchaseState.purchase.address,
                currentDepartment = purchaseState.currentDepartment,
                currentCoord = purchaseState.storeCoord,
                updateCurrentAddress = { purchaseViewModel.updateStoreAddress(it) },
                updateCurrentDepartment = { purchaseViewModel.updateCurrentDepartment(it) },
                updateCurrentCoord = { purchaseViewModel.updateStoreCoord(it) },
                cameraPosition = purchaseViewModel.getCameraPosition(),
                departments = purchaseState.departments,
                onAddAddressClick = { address, lat, lng ->
                    navController.popBackStack(PriceRecommenderScreen.AddPurchaseScreen.name, false)
                },
                onCancelClick = {
                    navController.popBackStack(PriceRecommenderScreen.AddPurchaseScreen.name, false)
                },
                emptyState = {  },
                updateCameraPosition = { latLng, zoom ->
                    purchaseViewModel.updateCameraPosition(latLng, zoom) }
            )
        }

            composable(route = PriceRecommenderScreen.SelectProductsScreen.name) {
                SelectProductsScreen(
                    productState = productState,
                    updateCurrentName = { productViewModel.updateCurrentName(it) },
                    updateCurrentAmount = { productViewModel.updateCurrentAmount(it) },
                    updateCurrentPrice = { productViewModel.updateCurrentPrice(it) },
                    updateCurrentBrand = { productViewModel.updateCurrentBrand(it) },
                    onAddProductClick = { name, amount, price, brand ->
                        purchaseViewModel.updateProductsList(name, amount, price, brand)
                        productViewModel.emptyState()
                        navController.popBackStack()
                    },
                    onReturnToPurchaseClick = { navController.popBackStack() }
                )
            }

            composable(route = PriceRecommenderScreen.PurchasesReportScreen.name) {
                PurchasesReportScreen(
                    uiState = purchaseReportState,
                    onPurchaseClick = {
                        purchaseReportViewModel.updateCurrentPurchase(it)
                        navController.navigate(PriceRecommenderScreen.SelectedPurchaseScreen.name)
                    },
                    onRetryClick = { purchaseReportViewModel.getReport(purchaseReportState.userId) }
                )
            }

            composable(route = PriceRecommenderScreen.SelectedPurchaseScreen.name) {
                SelectedPurchaseScreen(purchase = purchaseReportState.currentPurchase)
            }

            composable(route = PriceRecommenderScreen.CartScreen.name) {
                CartScreen(
                    cartState = cartState,
                    availableProducts = productState.products,
                    updateCurrentName = { cartViewModel.updateCurrentName(it) },
                    updateCurrentAmount = { cartViewModel.updateCurrentAmount(it) },
                    updateCurrentBrand = { cartViewModel.updateCurrentBrand(it) },
                    onAddProductToCart = { name, amount, brand, context ->
                        cartViewModel.addProductToCart(name, amount, brand, context)
                        cartViewModel.emptyState()
                    },
                    onDeleteProductFromCart = { product, context ->
                        cartViewModel.deleteProductFromCart(product, context)
                    },
                    onEmptyCart = { context -> cartViewModel.emptyCart(context) },
                    emptyState = {
                        cartViewModel.emptyState()
                    },
                    onRetryClick = { cartViewModel.getCurrentUserId() }
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

            composable(route = PriceRecommenderScreen.ErrorScreen.name) {
                val errorState = homeState.apiState as ApiUIState.Error
                ErrorScreen(
                    message = errorState.exception.message ?: errorState.defaultMessage,
                    onRetry = { navController.popBackStack() }
                )
            }


            composable(route = PriceRecommenderScreen.LoadingScreen.name) {
                LoadingScreen()
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