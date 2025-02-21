package com.example.dictionaryapp.feature_dictionary.domain.use_cases

import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity
import com.example.dictionaryapp.feature_dictionary.domain.repository.SavedRepository
import kotlinx.coroutines.flow.Flow

class GetSavedWords(
    private val repository: SavedRepository
) {
    operator fun invoke(): Flow<SavedState<List<FavoriteWordEntity>>> {
        return repository.getSavedWords()
    }
}
