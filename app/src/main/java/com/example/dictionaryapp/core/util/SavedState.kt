package com.example.dictionaryapp.core.util

sealed interface SavedState<out T> {
    data object Loading : SavedState<Nothing>
    data class Success<T>(val data: T) : SavedState<T>
    data class Error(val message: String) : SavedState<Nothing>
}
