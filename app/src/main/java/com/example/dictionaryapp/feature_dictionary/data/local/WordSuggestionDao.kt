package com.example.dictionaryapp.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordSuggestionEntity

@Dao
interface WordSuggestionDao {

    @Query("SELECT * FROM word_suggestions WHERE search_query = :query")
    suspend fun getWords(query: String): WordSuggestionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(wordSuggestion: WordSuggestionEntity)

    @Query("DELETE FROM word_suggestions WHERE search_query = :query")
    suspend fun deleteWords(query: String): Int
}
