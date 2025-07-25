package com.example.pricerecommender.ui.utils

import androidx.annotation.StringRes
import com.example.pricerecommender.R

enum class PriceRecommenderScreen(
    @StringRes val title: Int
) {
    HomeScreen(R.string.home_screen),
    CartScreen(R.string.create_shopping_list),
    CheckBestRouteScreen(R.string.check_the_best_route),
    AddPurchaseScreen(R.string.add_purchase),
    PurchasesReportScreen(R.string.purchases_report),
    AddressManualEntryScreen(R.string.manual_entry),
    SelectProductsScreen(R.string.select_products),
    ErrorScreen(R.string.error_screen),
    LoadingScreen(R.string.loading_screen),
    StoreAddressScreen(R.string.store_address),
    SelectedPurchaseScreen(R.string.selected_purchase_screen),
    ReceiptCaptureScreen(R.string.receipt_capture_screen),
    ModifyReceiptScreen(R.string.modify_receipt)
}
