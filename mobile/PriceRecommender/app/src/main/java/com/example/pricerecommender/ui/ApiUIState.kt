package com.example.pricerecommender.ui

sealed interface ApiUIState<out T> {
    data class Success<out T>(val data: T) : ApiUIState<T>
    data class Error(val exception: Throwable) : ApiUIState<Nothing>
    data object Loading : ApiUIState<Nothing>
}