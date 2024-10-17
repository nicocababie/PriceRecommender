package com.example.pricerecommender.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val addressRepository: IAddressRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PriceRecommenderUIState())
    var uiState: StateFlow<PriceRecommenderUIState> = _uiState

    init {
        getAllAddresses()
    }

    fun updateCurrentAddress(address: String) {
        _uiState.update { currentState ->
            currentState.copy(
                currentAddress = address
            )
        }
    }

    fun getAllAddresses() {
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
            getAllAddresses()
        }
    }

    fun deleteAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.delete(address)
            getAllAddresses()
        }
    }
}