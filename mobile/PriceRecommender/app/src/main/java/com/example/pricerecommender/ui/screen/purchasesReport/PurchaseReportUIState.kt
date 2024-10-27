package com.example.pricerecommender.ui.screen.purchasesReport

import com.example.pricerecommender.data.model.PurchaseData
import com.example.pricerecommender.data.model.PurchaseReport
import com.example.pricerecommender.ui.ApiUIState

data class PurchaseReportUIState(
    val userId: String = "",
    val report: PurchaseReport = PurchaseReport("", emptyList()),
    val currentPurchase: PurchaseData = PurchaseData("", "", emptyList(), ""),
    val apiState: ApiUIState<PurchaseReport> = ApiUIState.Loading
)
