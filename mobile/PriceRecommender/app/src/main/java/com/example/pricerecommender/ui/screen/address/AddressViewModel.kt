package com.example.pricerecommender.ui.screen.address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.repositoryInterface.IDepartmentRepository
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
class AddressViewModel @Inject constructor(
    private val departmentRepository: IDepartmentRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(AddressUIState())
    var uiState: StateFlow<AddressUIState> = _uiState

     init {
         getAllDepartments()
     }

//    _uiState.update { currentState ->
//        currentState.copy(
//
//        )
//    }

    fun emptyState() {
        _uiState.update { currentState ->
        currentState.copy(
            currentAddress = "",
            currentDepartment = "Select department",
            currentCoord = LatLng(0.0, 0.0)
        )
    }
    }

    fun updateCurrentAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(
               currentAddress =  address
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

    fun updateCurrentCoord(latLng: LatLng) {
        _uiState.update { currentState ->
        currentState.copy(
            currentCoord = latLng
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