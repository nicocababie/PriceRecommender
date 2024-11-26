package com.example.pricerecommender.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.IAddressRepository
import com.example.pricerecommender.data.repositoryInterface.IProductRepository
import com.example.pricerecommender.data.repositoryInterface.IUserRepository
import com.example.pricerecommender.ui.ApiUIState
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
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: IUserRepository,
    private val productRepository: IProductRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    var uiState: StateFlow<HomeUIState> = _uiState

    init {
        getCurrentAddress()
        getCurrentRange()
        getAllAddresses()
        getCurrentUserId()
    }

    fun updateCurrentAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { currentState ->
                addressRepository.getAddressByName(address)?.let {
                    currentState.copy(
                        currentAddress = it
                    )
                }!!
            }
            saveCurrentAddress(address)
        }

    }

    fun insertAddress(name: String, lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val address = addressRepository.insert(name, lat, lng)
            _uiState.update { currentState ->
                currentState.copy(
                    currentAddress = address
                )
            }
            saveCurrentAddress(name)
            getAllAddresses()
        }
    }

    fun deleteAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.delete(address)
            val allAddresses = addressRepository.getAllAddresses()
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
                saveCurrentAddress(firstAddress.address)
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
                    addresses = addressRepository.getAllAddresses()
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
        viewModelScope.launch(Dispatchers.IO) {
            preferencesRepository.userAddress
                .collect { savedAddress ->
                    val address = addressRepository.getAddressByName(savedAddress)
                    if (address != null){
                        _uiState.update { currentState ->
                            currentState.copy(
                                currentAddress = address
                            )
                        }
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
                    val rangeValue = if (savedRange.isEmpty()) 0.0f else savedRange.toFloat()
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentRange = rangeValue
                        )
                    }
                }
        }
    }

    private fun saveUserId(id: String) {
        viewModelScope.launch {
            preferencesRepository.saveUserId(id)
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            preferencesRepository.userId
                .collect { savedUserId ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            userId = savedUserId,
                            apiState = ApiUIState.Success(savedUserId)
                        )
                    }
                    if (savedUserId == "") {
                        _uiState.update { currentState ->
                            currentState.copy(
                                apiState = ApiUIState.Loading
                            )
                        }
                        try {
                            val user = userRepository.getUserId()
                            _uiState.update { currentState ->
                                currentState.copy(
                                    userId = user.id,
                                    apiState = ApiUIState.Success(user)
                                )
                            }
                            saveUserId(user.id)
                        } catch (e: Exception) {
                            _uiState.update { currentState ->
                                currentState.copy(
                                    apiState = ApiUIState.Error(e)
                                )
                            }
                        }
                    }
                }

        }
    }
}