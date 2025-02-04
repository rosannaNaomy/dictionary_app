package com.example.dictionaryapp.feature_dictionary.domain.use_cases

import com.example.dictionaryapp.core.util.NetworkState
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow

class GetSuggestedWords(private val repository: WordInfoRepository) {
    suspend operator fun invoke(query: String): Flow<NetworkState<List<String>>> {
        return repository.getSuggestedWords(query)
    }
}