package com.example.pricerecommender.ui.screen.bestRoute

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BestRouteViewModel @Inject constructor(): ViewModel() {
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

    fun getMarkers(): List<MarkerState> {
        return _uiState.value.markers.map { latLong ->
            MarkerState(position = latLong)
        }
    }
}