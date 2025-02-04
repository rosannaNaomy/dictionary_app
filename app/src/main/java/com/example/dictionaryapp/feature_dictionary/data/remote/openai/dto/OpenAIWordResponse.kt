package com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenAIWordResponse(
    val choices: List<Choice>
)