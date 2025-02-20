package com.example.dictionaryapp.feature_dictionary.presentation.saved_words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetSavedWordStatus
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetSavedWords
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetToggleSavedWordStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedWords: GetSavedWords,
    private val getSavedWordStatus: GetSavedWordStatus,
    private val getToggleSavedWordStatus: GetToggleSavedWordStatus
) : ViewModel() {

    private val _savedWordsState = MutableStateFlow(SavedWordsState()) // ✅ Use new state class
    val savedWordsState: StateFlow<SavedWordsState> = _savedWordsState.asStateFlow()

    private val _isWordSavedState = MutableStateFlow<SavedState<Boolean>>(SavedState.Loading)
    val isWordSavedState: StateFlow<SavedState<Boolean>> = _isWordSavedState.asStateFlow()

    init {
        loadSavedWords()
    }

    fun loadSavedWords() {
        viewModelScope.launch {
            getSavedWords().collect { state ->
                _savedWordsState.value = when (state) {
                    is SavedState.Loading -> SavedWordsState(isLoading = true)
                    is SavedState.Success -> SavedWordsState(savedWords = state.data)
                    is SavedState.Error -> SavedWordsState(errorMessage = state.message)
                }
            }
        }
    }

    fun checkIfWordIsSaved(word: String) {
        viewModelScope.launch {
            getSavedWordStatus(word).collect { state ->
                _isWordSavedState.value = state
            }
        }
    }

    fun toggleWord(wordInfo: WordInfo) {
        viewModelScope.launch {
            val result = getToggleSavedWordStatus(wordInfo)
            if (result is SavedState.Success) {
                loadSavedWords() // Refresh after saving/removing
            }
        }
    }
}

