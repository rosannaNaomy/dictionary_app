package com.example.dictionaryapp.feature_dictionary.domain.repository

import com.example.dictionaryapp.core.util.NetworkState
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word: String): Flow<NetworkState<List<WordInfo>>>
    fun getSuggestedWords(query: String): Flow<NetworkState<List<String>>>
}
