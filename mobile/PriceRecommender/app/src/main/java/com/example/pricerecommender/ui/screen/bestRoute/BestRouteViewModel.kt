package com.example.pricerecommender.ui.screen.bestRoute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.MarkerDetail
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestRouteViewModel @Inject constructor(
    private val productRepository: IProductRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(BestRouteUIState())
    var uiState: StateFlow<BestRouteUIState> = _uiState

    fun updateCameraPosition(position: LatLng, zoom: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                cameraPosition = CameraPosition.fromLatLngZoom(position, zoom)
            )
        }
    }

    fun updateIsLoadedMap(isLoaded: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isMapLoaded = isLoaded
            )
        }
    }

    fun getCameraPosition(): CameraPositionState {
        return CameraPositionState(_uiState.value.cameraPosition)
    }

    fun getBestRoute(userId : String, addressLat: Double, addressLng: Double, range: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val route = productRepository.getBestRoute(
                userId = userId,
                addressLat = addressLat,
                addressLng = addressLng,
                range = range
            ).map {
                MarkerDetail(
                    storeName = it.storeName,
                    storeLatLng = MarkerState(position = LatLng(it.storeLat, it.storeLng)),
                    productName = it.productName,
                    productPrice = it.price
                )
            }
            _uiState.update { currentState ->
                currentState.copy(
                    details = route
                )
            }
        }
    }
}