package com.example.dictionaryapp.feature_dictionary.data.repository

import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.data.local.FavoriteWordDao
import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.SavedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SavedRepositoryImpl(
    private val favoriteWordDao: FavoriteWordDao
) : SavedRepository {

    override fun getSavedWords(): Flow<SavedState<List<FavoriteWordEntity>>> = flow {
        emit(SavedState.Loading)
        try {
            val words = favoriteWordDao.getAllFavoriteWords().first()
            emit(SavedState.Success(words))
        } catch (e: Exception) {
            emit(SavedState.Error("Failed to load saved words: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun isWordSaved(word: String): Flow<SavedState<Boolean>> = flow {
        emit(SavedState.Loading)
        try {
            val isSaved = favoriteWordDao.isFavorite(word)
            emit(SavedState.Success(isSaved))
        } catch (e: Exception) {
            emit(SavedState.Error("Failed to check favorite status: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun toggleSavedWord(wordInfo: WordInfo): SavedState<String> {
        return try {
            if (favoriteWordDao.isFavorite(wordInfo.word)) {
                favoriteWordDao.removeFavoriteWord(wordInfo.word)
                SavedState.Success("Word removed from favorites")
            } else {
                favoriteWordDao.insertFavoriteWord(
                    FavoriteWordEntity(
                        word = wordInfo.word,
                        phonetic = wordInfo.phonetic,
                        meanings = wordInfo.meanings
                    )
                )
                SavedState.Success("Word saved to favorites")
            }
        } catch (e: Exception) {
            SavedState.Error("Failed to toggle favorite: ${e.localizedMessage}")
        }
    }
}
