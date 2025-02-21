package com.example.dictionaryapp.feature_dictionary.domain.use_cases

import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.SavedRepository

class GetToggleSavedWordStatus(
    private val repository: SavedRepository
) {
    suspend operator fun invoke(wordInfo: WordInfo): SavedState<String> {
        return repository.toggleSavedWord(wordInfo)
    }
}
