package com.example.dictionaryapp.feature_dictionary.data.remote.dto

import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.squareup.moshi.Json

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    @Json(name = "phonetic")
    val phonetic: String?,
    val phonetics: List<PhoneticDto>?,
    val word: String
){
    fun toWordInfoEntity(): WordInfoEntity{
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}
