package com.example.dictionaryapp.feature_dictionary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.feature_dictionary.data.local.converters.Converters
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordSuggestionEntity

@Database(
    entities = [WordInfoEntity::class,  WordSuggestionEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {
    abstract fun wordInfoDao(): WordInfoDao
    abstract fun wordSuggestionDao(): WordSuggestionDao
}