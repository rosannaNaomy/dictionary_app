package com.example.dictionaryapp.feature_dictionary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_suggestions")
data class WordSuggestionEntity(
    @PrimaryKey
    @ColumnInfo(name = "search_query")
    val query: String,

    val words: List<String>
)
