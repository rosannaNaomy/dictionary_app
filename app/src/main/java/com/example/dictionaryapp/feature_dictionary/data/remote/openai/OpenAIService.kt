package com.example.dictionaryapp.feature_dictionary.data.remote.openai

import com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto.ChatRequestBody
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto.OpenAIWordResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIService {
    @POST("chat/completions")
    suspend fun getChatCompletion(
        @Header("Authorization") authHeader: String,
        @Body requestBody: ChatRequestBody
    ): OpenAIWordResponse
}
