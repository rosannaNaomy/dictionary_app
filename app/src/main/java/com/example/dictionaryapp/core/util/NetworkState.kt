package com.example.dictionaryapp.core.util

import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo


sealed interface NetworkState<out T> {
    data object Loading : NetworkState<Nothing>
    data class Success<T>(val data: T) : NetworkState<T>
    data class Error(val message: String) : NetworkState<Nothing>
}
//sealed interface NetworkState {
//    data object Loading: NetworkState
//    data class Success(val data: List<WordInfo>) : NetworkState
//    data class Error(val message: String) : NetworkState
//}
