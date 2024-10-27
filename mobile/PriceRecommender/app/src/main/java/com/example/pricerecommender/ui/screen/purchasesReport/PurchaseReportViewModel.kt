package com.example.pricerecommender.ui.screen.purchasesReport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.PurchaseData
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseReportViewModel @Inject constructor(
    private val purchaseRepository: IPurchaseRepository,
    private val preferencesRepository: PreferencesRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(PurchaseReportUIState())
    var uiState: StateFlow<PurchaseReportUIState> = _uiState

    init {
        getCurrentUserId()
    }

    fun updateCurrentPurchase(purchase: PurchaseData) {
        _uiState.update { currentState ->
            currentState.copy(
                currentPurchase = purchase
            )
        }
    }

    private fun getCurrentUserId() {
        viewModelScope.launch {
            preferencesRepository.userId
                .collect { savedUserId ->
                    _uiState.update { currentState ->
                        currentState.copy(userId = savedUserId)
                    }
                    getReport(savedUserId)
                }
        }
    }

    fun getReport(userId: String) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(
                        report = purchaseRepository.getReport(userId)
                    )
                }
            } catch (_: Exception) {

            }
        }
    }
}