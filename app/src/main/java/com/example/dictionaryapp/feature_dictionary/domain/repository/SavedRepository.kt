package com.example.dictionaryapp.feature_dictionary.domain.repository

import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface SavedRepository {
    suspend fun toggleSavedWord(wordInfo: WordInfo): SavedState<String>
    fun getSavedWords(): Flow<SavedState<List<FavoriteWordEntity>>>
    fun isWordSaved(word: String): Flow<SavedState<Boolean>>
}