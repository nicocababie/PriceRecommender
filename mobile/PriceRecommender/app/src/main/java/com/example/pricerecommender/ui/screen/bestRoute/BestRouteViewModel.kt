package com.example.pricerecommender.ui.screen.bestRoute

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.MarkerDetail
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import com.example.pricerecommender.ui.ApiUIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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
            try {
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
                val boundsBuilder = LatLngBounds.builder()
                route.forEach { detail ->
                    boundsBuilder.include(detail.storeLatLng.position)
                }
                val bounds = boundsBuilder.build()
                _uiState.update { currentState ->
                    currentState.copy(
                        details = route,
                        apiState = ApiUIState.Success(route),
                        cameraPosition = CameraPosition.fromLatLngZoom(bounds.center, getZoomForBounds(bounds))
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(apiState = ApiUIState.Error(e))
                }
            }
        }
    }

    private fun getZoomForBounds(bounds: LatLngBounds): Float {
        val northeast = bounds.northeast
        val southwest = bounds.southwest

        val earthRadius = 6371.0

        val latDiff = Math.toRadians(northeast.latitude - southwest.latitude)
        val lngDiff = Math.toRadians(northeast.longitude - southwest.longitude)

        val a = sin(latDiff / 2).pow(2.0) +
                cos(Math.toRadians(southwest.latitude)) * cos(Math.toRadians(northeast.latitude)) *
                sin(lngDiff / 2).pow(2.0)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val distanceKm = earthRadius * c

        return when {
            distanceKm > 20 -> 7f
            distanceKm > 10 -> 9f
            distanceKm > 5 -> 11f
            else -> 13f
        }
    }
}