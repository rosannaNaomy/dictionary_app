package com.example.dictionaryapp.core.util

sealed interface NetworkState<out T> {
    data object Loading : NetworkState<Nothing>
    data class Success<T>(val data: T) : NetworkState<T>
    data class Error(val message: String) : NetworkState<Nothing>
}