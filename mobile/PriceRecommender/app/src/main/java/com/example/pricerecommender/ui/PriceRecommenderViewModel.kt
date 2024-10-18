package com.example.pricerecommender.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PriceRecommenderViewModel @Inject constructor(
    private val addressRepository: IAddressRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PriceRecommenderUIState())
    var uiState: StateFlow<PriceRecommenderUIState> = _uiState

    init {
        getCurrentAddress()
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

    fun insertAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.insert(address)
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

    private fun saveCurrentAddress(currentAddress: String) {
        viewModelScope.launch {
            preferencesRepository.saveUserAddress(currentAddress)
        }
    }

    private fun getCurrentAddress(){
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
}