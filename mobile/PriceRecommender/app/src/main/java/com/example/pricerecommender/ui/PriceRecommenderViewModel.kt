package com.example.pricerecommender.ui

import androidx.lifecycle.ViewModel
import com.example.pricerecommender.data.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PriceRecommenderViewModel @Inject constructor(
    private val addressRepository: AddressRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PriceRecommenderUIState())
    var uiState: StateFlow<PriceRecommenderUIState> = _uiState


}