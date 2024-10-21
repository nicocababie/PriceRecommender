package com.example.pricerecommender.ui.screen.purchase

import androidx.lifecycle.ViewModel
import com.example.pricerecommender.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(PurchaseUIState())
    var uiState: StateFlow<PurchaseUIState> = _uiState

    fun updateStoreName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                purchase = currentState.purchase.copy(
                    name = name
                )
            )
        }
    }

    fun updateStoreAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(
                purchase = currentState.purchase.copy(
                    address = address
                )
            )
        }
    }

    fun updateProductsList(name: String, amount: String, price: String, brand: String) {
        val store = _uiState.value.purchase.address
        val product = Product(name, amount, price, brand, store)
        _uiState.update { currentState ->
            val updatedProducts = currentState.purchase.products + product
            currentState.copy(
                purchase = currentState.purchase.copy(
                    products = updatedProducts
                )
            )
        }
    }
}