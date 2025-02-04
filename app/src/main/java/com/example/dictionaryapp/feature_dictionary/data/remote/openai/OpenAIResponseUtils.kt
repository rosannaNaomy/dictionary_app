package com.example.dictionaryapp.feature_dictionary.data.remote.openai

import com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto.OpenAIWordResponse

fun OpenAIWordResponse.extractWords(): List<String> {
    val content = this.choices.firstOrNull()?.message?.content ?: ""
    return content.split("\n")
        .map { it.replace(Regex("[^a-zA-Z]"), "").trim() }
        .filter { it.isNotEmpty() }
}