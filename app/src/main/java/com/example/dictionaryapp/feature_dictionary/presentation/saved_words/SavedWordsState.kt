package com.example.dictionaryapp.feature_dictionary.presentation.saved_words

import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity

data class SavedWordsState(
    val savedWords: List<FavoriteWordEntity> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
