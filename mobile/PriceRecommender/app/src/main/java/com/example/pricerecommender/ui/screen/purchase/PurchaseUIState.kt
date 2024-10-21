package com.example.pricerecommender.ui.screen.purchase

import com.example.pricerecommender.data.model.Purchase

data class PurchaseUIState(
    val purchase: Purchase = Purchase(name = "", address = "", products = emptyList())
)
