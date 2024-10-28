package com.example.pricerecommender.ui.screen.cart

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.CartProduct
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.ICartRepository
import com.example.pricerecommender.ui.ApiUIState
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
        val newProduct = CartProduct(null, name, amount, brand)
        viewModelScope.launch {
            try {
                val cart = _uiState.value.cart
                if (cart.isEmpty()) {
                    val newCart = _uiState.value.cart + newProduct
                    cartRepository.add(newCart, userId)
                    _uiState.update { currentState ->
                        currentState.copy(
                            cart = newCart
                        )
                    }
                }
                else if (cart.map { it.name }.contains(name)) {
                    val updatedCart = cart.filter { it.name != name } + newProduct
                    cartRepository.update(updatedCart, userId)
                    _uiState.update { currentState ->
                        currentState.copy(
                            cart = updatedCart
                        )
                    }
                }
                else {
                    val newCart = _uiState.value.cart + newProduct
                    cartRepository.update(newCart, userId)
                    _uiState.update { currentState ->
                        currentState.copy(
                            cart = newCart
                        )
                    }
                }
                Toast.makeText(
                    context,
                    "Product added successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Server Error: Error while adding product to cart",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun deleteProductFromCart(
        product: CartProduct,
        context: Context
    ) {
        val userId = _uiState.value.userId
        val updatedCart = _uiState.value.cart - product
        viewModelScope.launch {
            try {
                cartRepository.update(updatedCart, userId)
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
                    "Server Error: Error while deleting product from cart",
                    Toast.LENGTH_LONG
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
                _uiState.update { currentState ->
                    currentState.copy(
                        cart = emptyList()
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Server Error: Error while deleting products from cart",
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

    fun emptyState() {
        _uiState.update { currentState ->
            currentState.copy(
                currentName = "Name",
                currentAmount = 0,
                currentBrand = "Brand"
            )
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            preferencesRepository.userId
                .collect { savedUserId ->
                    _uiState.update { currentState ->
                        currentState.copy(userId = savedUserId)
                    }
                    getCurrentCart(savedUserId)
                }
        }
    }

    private fun getCurrentCart(userId: String) {
        viewModelScope.launch {
            try {
                val cart = cartRepository.getCart(userId)
                _uiState.update { currentState ->
                    currentState.copy(
                        cart = cart,
                        apiState = ApiUIState.Success(cart)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(apiState = ApiUIState.Error(e))
                }
            }
        }
    }
}