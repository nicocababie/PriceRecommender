package com.example.pricerecommender.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import com.example.pricerecommender.data.repositoryInterface.IDepartmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addressRepository: IAddressRepository,
    private val departmentRepository: IDepartmentRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    var uiState: StateFlow<HomeUIState> = _uiState

    init {
        getCurrentAddress()
        getCurrentRange()
        getAllAddresses()
    }

    fun updateCurrentAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentAddress = address
            )
        }
        saveCurrentAddress(address)
    }

    fun insertAddress(address: String, lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.insert(address, lat, lng)
            _uiState.update { currentState ->
                currentState.copy(
                    currentAddress = address
                )
            }
            saveCurrentAddress(address)
            getAllAddresses()
        }
    }

    fun deleteAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.delete(address)
            val allAddresses = addressRepository.getAllAddresses().map { it.address }
            _uiState.update { currentState ->
                currentState.copy(
                    addresses = allAddresses,
                )
            }
            if (allAddresses.isNotEmpty()) {
                val firstAddress = allAddresses.first()
                _uiState.update { currentState ->
                    currentState.copy(
                        currentAddress = firstAddress
                    )
                }
                saveCurrentAddress(firstAddress)
            }
        }
    }

    fun updateCurrentRange(range: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                currentRange = range
            )
        }
        saveCurrentRange(range)
    }

    private fun getAllAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                currentState.copy(
                    addresses = addressRepository.getAllAddresses().map {
                        it.address
                    }
                )
            }
        }
    }

    private fun saveCurrentAddress(currentAddress: String) {
        viewModelScope.launch {
            preferencesRepository.saveUserAddress(currentAddress)
        }
    }

    private fun getCurrentAddress() {
        viewModelScope.launch {
            preferencesRepository.userAddress
                .collect { savedAddress ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentAddress = savedAddress
                        )
                    }
                }
        }
    }

    private fun saveCurrentRange(currentRange: Float) {
        viewModelScope.launch {
            preferencesRepository.saveUserRange(currentRange)
        }
    }

    private fun getCurrentRange() {
        viewModelScope.launch {
            preferencesRepository.userRange
                .collect { savedRange ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentRange = savedRange.toFloat()
                        )
                    }
                }
        }
    }
}