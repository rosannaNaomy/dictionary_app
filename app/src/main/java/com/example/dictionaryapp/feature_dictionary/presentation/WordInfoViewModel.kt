package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.NetworkState
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

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
                            _eventFlow.emit(UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            ))
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