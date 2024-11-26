package com.example.pricerecommender.ui.screen.purchase

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.app.Product
import com.example.pricerecommender.data.model.app.Purchase
import com.example.pricerecommender.data.repositoryInterface.IDepartmentRepository
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import com.example.pricerecommender.ui.ApiUIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val purchaseRepository: IPurchaseRepository,
    private val departmentRepository: IDepartmentRepository,
): ViewModel() {
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

    fun updateProductsList(name: String, amount: Int, price: Double, brand: String) {
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

    fun updateStoreCoord(coord: LatLng) {
        _uiState.update { currentState ->
            currentState.copy(
                storeCoord = coord
            )
        }
    }

    fun addPurchase(
        userId: String,
        storeName: String,
        storeAddress: String,
        products: List<Product>,
        coord: LatLng,
        context: Context
    ) {
        viewModelScope.launch {
            try {
                purchaseRepository.add(userId, storeName, storeAddress, products, coord)
                Toast.makeText(
                    context,
                    "Purchase added successfully",
                    Toast.LENGTH_SHORT
                ).show()
                emptyState()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Server Error: Error while adding purchase",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    init {
        getAllDepartments()
    }

    private fun emptyState() {
        _uiState.update { currentState ->
            currentState.copy(
                purchase = Purchase("", "", emptyList()),
                currentDepartment = "Select department",
                storeCoord = LatLng(0.0, 0.0)
            )
        }
    }

    fun updateCurrentDepartment(department: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentDepartment = department
            )
        }
    }

    fun updateCameraPosition(position: LatLng, zoom: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                cameraPosition = CameraPosition.fromLatLngZoom(position, zoom)
            )
        }
    }

    fun getCameraPosition(): CameraPositionState {
        return CameraPositionState(_uiState.value.cameraPosition)
    }

    private fun getAllDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    departments = departmentRepository.getAllDepartments().map {
                        it.name
                    }
                )
            }
        }
    }

    fun updateSelectedProduct(product: Product) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProduct = product
            )
        }
    }

    fun clearSelectedProduct() {
        _uiState.update { currentState ->
            currentState.copy(
                selectedProduct = Product("", 0, 0.0, "", "")
            )
        }
    }

    fun updateReceipt(
        oldProduct: Product,
        name: String, amount: Int, price: Double, brand: String,
    ) {
        _uiState.update { currentState ->
            val updatedProduct = Product(name, amount, price, brand, oldProduct.store)
            val updatedReceipt = currentState.receipt.map { product ->
                if (product.name == oldProduct.name) updatedProduct else product
            }
            currentState.copy(
                receipt = updatedReceipt
            )
        }
    }

    fun updateImageUri(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(
                imageUri = uri
            )
        }
    }

    fun clearImageUri() {
        _uiState.update { currentState ->
            currentState.copy(
                imageUri = Uri.EMPTY
            )
        }
    }

    fun submitReceipt(
        imageUri: Uri,
        storeLat: Double,
        storeLng: Double,
        userId: String,
        context: Context
    ) {
        _uiState.update { currentState ->
            currentState.copy(apiState = ApiUIState.Loading)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = purchaseRepository.addReceipt(
                    imageUri,
                    storeLat,
                    storeLng,
                    userId,
                    context
                )
                _uiState.update { currentState ->
                    currentState.copy(
                        apiState = ApiUIState.Success(result.size),
                        receipt = result
                    )
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "${result.size} product/s loaded successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(apiState = ApiUIState.Error(e))
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Server Error: Error while loading receipt products",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}