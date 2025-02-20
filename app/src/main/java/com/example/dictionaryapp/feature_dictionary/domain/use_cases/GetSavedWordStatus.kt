package com.example.dictionaryapp.feature_dictionary.domain.use_cases

import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.domain.repository.SavedRepository
import kotlinx.coroutines.flow.Flow

class GetSavedWordStatus(
    private val repository: SavedRepository
) {
    operator fun invoke(word: String): Flow<SavedState<Boolean>> {
        return repository.isWordSaved(word)
    }
}
