package com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Choice(
    val message: Message
)