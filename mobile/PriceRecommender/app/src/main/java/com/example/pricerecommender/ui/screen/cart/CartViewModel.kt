package com.example.pricerecommender.ui.screen.cart

import androidx.lifecycle.ViewModel
import com.example.pricerecommender.data.model.CartProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(): ViewModel() {
    private val _uiState = MutableStateFlow(CartUIState())
    var uiState: StateFlow<CartUIState> = _uiState

    fun updateCart(name: String, amount: String, brand: String) {
        val cartProduct = CartProduct(name, amount, brand)
        _uiState.update { currentState ->
            val updatedCart = currentState.cart + cartProduct
            currentState.copy(
                cart = updatedCart
            )
        }
    }

}