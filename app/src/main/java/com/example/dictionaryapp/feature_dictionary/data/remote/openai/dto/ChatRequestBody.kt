package com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto

import com.squareup.moshi.Json

data class ChatRequestBody(
    val model: String,
    val messages: List<Message>,
    val temperature: Double = 0.0,
    @Json(name = "max_tokens")
    val maxTokens: Int
)