package com.example.pricerecommender.ui.screen.product

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ProductViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState

    fun updateCurrentName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentName = name
            )
        }
    }

    fun updateCurrentAmount(amount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currentAmount = amount
            )
        }
    }

    fun updateCurrentPrice(price: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                currentPrice = price
            )
        }
    }

    fun updateCurrentBrand(brand: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentBrand = brand
            )
        }
    }
}