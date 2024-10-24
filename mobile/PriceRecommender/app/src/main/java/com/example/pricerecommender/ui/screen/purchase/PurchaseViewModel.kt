package com.example.pricerecommender.ui.screen.purchase

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.Product
import com.example.pricerecommender.data.repositoryInterface.IDepartmentRepository
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
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
                purchase = currentState.purchase.copy(
                    address = ""
                ),
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
}