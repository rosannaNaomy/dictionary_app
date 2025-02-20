package com.example.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning

@Entity(tableName = "favorite_words")
data class FavoriteWordEntity(
    @PrimaryKey val word: String,
    val phonetic: String,
    val meanings: List<Meaning>,
    val savedAt: Long = System.currentTimeMillis()
)
