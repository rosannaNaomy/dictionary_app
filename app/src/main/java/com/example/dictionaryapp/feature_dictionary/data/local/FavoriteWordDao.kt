package com.example.dictionaryapp.feature_dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteWordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteWord(favoriteWord: FavoriteWordEntity)

    @Query("DELETE FROM favorite_words WHERE word = :word")
    suspend fun removeFavoriteWord(word: String)

    @Query("SELECT * FROM favorite_words")
    fun getAllFavoriteWords(): Flow<List<FavoriteWordEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_words WHERE word = :word)")
    suspend fun isFavorite(word: String): Boolean
}
