package com.example.pricerecommender.ui.screen.purchasesReport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pricerecommender.data.model.app.PurchaseData
import com.example.pricerecommender.data.repository.PreferencesRepository
import com.example.pricerecommender.data.repositoryInterface.IPurchaseRepository
import com.example.pricerecommender.ui.ApiUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
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
                val report = purchaseRepository.getReport(userId)
                _uiState.update { currentState ->
                    currentState.copy(
                        report = purchaseRepository.getReport(userId).map {
                            PurchaseData(
                                storeName = it.storeName,
                                storeAddress = it.storeAddress,
                                products = it.products,
                                date = formatDateString(it.date)
                            )
                        },
                        apiState = ApiUIState.Success(report)
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(apiState = ApiUIState.Error(e))
                }
            }
        }
    }

    private fun formatDateString(isoDate: String): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        originalFormat.timeZone = TimeZone.getTimeZone("UTC")

        val desiredFormat = SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault())

        val date: Date = originalFormat.parse(isoDate)
        return desiredFormat.format(date)
    }
}