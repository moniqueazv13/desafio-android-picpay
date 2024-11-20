package com.picpay.desafio.android.presentation.user

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val value: T) : UiState<T>()
    data class Error(val error: Throwable) : UiState<Nothing>()
}