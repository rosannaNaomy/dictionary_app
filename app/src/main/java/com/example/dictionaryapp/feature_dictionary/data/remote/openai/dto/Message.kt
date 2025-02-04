package com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val role: String,
    val content: String
)