package com.example.pricerecommender.ui.screen.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.CartProduct
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.ICartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: ICartRepository,
    private val preferencesRepository: PreferencesRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(CartUIState())
    var uiState: StateFlow<CartUIState> = _uiState

    init {
        getCurrentUserId()
    }

    fun addProductToCart(
        name: String,
        amount: Int,
        brand: String,
        context: Context
    ) {
        val userId = _uiState.value.userId
        val cartProduct = CartProduct(name, amount, brand)
        val updatedCart = _uiState.value.cart + cartProduct
        viewModelScope.launch {
            try {
                cartRepository.add(updatedCart, userId)
                Toast.makeText(
                    context,
                    "Product added successfully",
                    Toast.LENGTH_SHORT
                ).show()
                _uiState.update { currentState ->
                    currentState.copy(
                        cart = updatedCart
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun deleteProductFromCart(
        name: String,
        amount: Int,
        brand: String,
        context: Context
    ) {
        val userId = _uiState.value.userId
        val cartProduct = CartProduct(name, amount, brand)
        val updatedCart = _uiState.value.cart - cartProduct
        viewModelScope.launch {
            try {
                cartRepository.delete(updatedCart, userId)
                Toast.makeText(
                    context,
                    "Product deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
                _uiState.update { currentState ->
                    currentState.copy(
                        cart = updatedCart
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun emptyCart(context: Context) {
        val userId = _uiState.value.userId
        viewModelScope.launch {
            try {
                cartRepository.empty(userId)
                Toast.makeText(
                    context,
                    "Cart emptied successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

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

    fun updateCurrentBrand(brand: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentBrand = brand
            )
        }
    }

    private fun getCurrentUserId() {
        viewModelScope.launch {
            preferencesRepository.userId
                .collect { savedUserId ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userId = savedUserId
                        )
                    }
                }
        }
    }
}