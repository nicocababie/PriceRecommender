package com.example.pricerecommender.ui.screen.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: IProductRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState

    init {
        getProducts()
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

    fun emptyState() {
        _uiState.update { currentState ->
            currentState.copy(
                currentName = "",
                currentAmount = 0,
                currentPrice = 0.0,
                currentBrand = ""
            )
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        products = productRepository.getProducts()
                    )
                }
            } catch (_: Exception) {

            }
        }
    }
}