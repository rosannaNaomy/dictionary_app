package com.example.dictionaryapp.feature_dictionary.data.repository

import com.example.dictionaryapp.core.util.NetworkState
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapp.feature_dictionary.data.local.WordSuggestionDao
import com.example.dictionaryapp.feature_dictionary.data.local.entity.WordSuggestionEntity
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.OpenAIService
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto.ChatRequestBody
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto.Message
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.dto.OpenAIWordResponse
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.extractWords
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException

class WordInfoRepositoryImpl(
    private val dictionaryApi: DictionaryApi,
    private val openAIService: OpenAIService,
    private val wordInfoDao: WordInfoDao,
    private val wordSuggestionDao: WordSuggestionDao,
    private val apiKey: String
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<NetworkState<List<WordInfo>>>  = flow { //Flow<NetworkState> ??
        emit(NetworkState.Loading)
        val wordInfo = wordInfoDao.getWordInfo(word).map { it.toWordInfo() }
        if (wordInfo.isEmpty()) {
            emit(fetchRemote(word))
        }else{
            emit(NetworkState.Success(wordInfo))
        }

    }

    override fun getSuggestedWords(query: String): Flow<NetworkState<List<String>>> = flow {
        val cachedSuggestions = wordSuggestionDao.getWords(query)
        if (cachedSuggestions != null) {
            emit(NetworkState.Success(cachedSuggestions.words))
            return@flow
        }

        emit(NetworkState.Loading) // Show loading indicator

        try {
            val requestBody = ChatRequestBody(
                model = "gpt-3.5-turbo",
                messages = listOf(
                    Message(role = "user", content = "List common English words that start with \"$query\".")
                ),
                temperature = 0.0,
                maxTokens = 50
            )

            val response: OpenAIWordResponse = openAIService.getChatCompletion("Bearer $apiKey", requestBody)

            val words = response.extractWords()

            wordSuggestionDao.insertWords(WordSuggestionEntity(query, words))

            emit(NetworkState.Success(words))

        } catch (e: IOException) {
            emit(NetworkState.Error("Check your internet connection."))
        } catch (e: HttpException) {
            emit(NetworkState.Error("Server error, try again later."))
        }
    }.flowOn(Dispatchers.IO)


    private suspend fun fetchRemote(
        word: String
    ): NetworkState<List<WordInfo>> { //NetworkState ??
        try {
            val remoteWordInfo = dictionaryApi.getWordInfo(word)
            wordInfoDao.deleteWordInfo(remoteWordInfo.map { it.word })
            wordInfoDao.insertWordInfo(remoteWordInfo.map { it.toWordInfoEntity() })
        } catch (e: IOException) {
            return NetworkState.Error(
                message = "Oop something went wrong!",
            )

        } catch (e: HttpException) {
            return NetworkState.Error(
                message = "Couldn't reach server, check your internet connection.",
                )
        }
        val newWordInfo = wordInfoDao.getWordInfo(word).map { it.toWordInfo() }
        return NetworkState.Success(newWordInfo)
    }
}