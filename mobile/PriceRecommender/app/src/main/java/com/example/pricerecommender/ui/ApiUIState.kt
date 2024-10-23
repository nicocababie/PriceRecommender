package com.example.pricerecommender.ui

sealed interface ApiUIState<out T> {
    data class Success<out T>(val data: T) : ApiUIState<T>
    data class Error(val exception: Throwable, val defaultMessage: String = "An unknown error has occurred, please try again later") : ApiUIState<Nothing>
    data object Loading : ApiUIState<Nothing>
}