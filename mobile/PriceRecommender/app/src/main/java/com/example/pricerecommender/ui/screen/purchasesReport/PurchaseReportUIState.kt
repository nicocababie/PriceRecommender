package com.example.pricerecommender.ui.screen.purchasesReport

import com.example.pricerecommender.data.model.app.PurchaseData
import com.example.pricerecommender.ui.ApiUIState

data class PurchaseReportUIState(
    val userId: String = "",
    val report: List<PurchaseData> = emptyList(),
    val currentPurchase: PurchaseData = PurchaseData("", "", emptyList(), ""),
    val apiState: ApiUIState<List<PurchaseData>> = ApiUIState.Loading
)
