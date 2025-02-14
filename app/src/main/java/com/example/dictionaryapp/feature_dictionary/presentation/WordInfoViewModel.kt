package com.example.dictionaryapp.feature_dictionary.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.NetworkState
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetSuggestedWords
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo,
    private val getSuggestedWords: GetSuggestedWords
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _suggestions = mutableStateOf<List<String>>(emptyList())
    val suggestions: State<List<String>> = _suggestions

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)

            Log.d("ViewModel", "Fetching suggestions for: $query")

            getSuggestedWords(query).collectLatest { result ->
                when (result) {
                    is NetworkState.Success -> {
                        Log.d("ViewModel", "Received suggestions: ${result.data}")
                        withContext(Dispatchers.Main) {
                            _suggestions.value = result.data
                        }
                    }

                    is NetworkState.Error -> {
                        Log.e("ViewModel", "Error fetching suggestions: ${result.message}")
                    }

                    is NetworkState.Loading -> {
                        Log.d("ViewModel", "Loading suggestions...")
                    }
                }
            }
        }
    }


    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfo(query)
                .onEach { result ->
                    when (result) {
                        is NetworkState.Success -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data,
                                isLoading = false
                            )
                        }

                        is NetworkState.Error -> {
                            _state.value = state.value.copy(
                                wordInfoItems = emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(
                                UIEvent.ShowSnackBar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is NetworkState.Loading -> {
                            _state.value = state.value.copy(
                                wordInfoItems = emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }

}