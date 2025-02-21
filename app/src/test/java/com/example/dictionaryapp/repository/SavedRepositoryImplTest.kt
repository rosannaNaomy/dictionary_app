package com.example.dictionaryapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.data.local.FavoriteWordDao
import com.example.dictionaryapp.feature_dictionary.data.local.entity.FavoriteWordEntity
import com.example.dictionaryapp.feature_dictionary.data.repository.SavedRepositoryImpl
import com.example.dictionaryapp.feature_dictionary.domain.model.Definition
import com.example.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SavedRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: SavedRepositoryImpl
    private val favoriteWordDao: FavoriteWordDao = mockk()

    private val wordInfo = WordInfo(
        word = "hello",
        phonetic = "həˈloʊ",
        meanings = listOf(
            Meaning(
                partOfSpeech = "noun",
                definitions = listOf(
                    Definition(
                        definition = "A greeting or expression of goodwill",
                        example = "She said 'hello' and waved.",
                        synonyms = listOf("hi", "hey", "greetings"),
                        antonyms = listOf("goodbye", "farewell")
                    )
                )
            )
        )
    )

    @Before
    fun setUp() {
        repository = SavedRepositoryImpl(favoriteWordDao)
    }

    @Test
    fun `getSavedWords emits loading and success state`() = runTest {
        val fakeWords = listOf( FavoriteWordEntity(
            word = wordInfo.word,
            phonetic = wordInfo.phonetic,
            meanings = wordInfo.meanings
        ))
        coEvery { favoriteWordDao.getAllFavoriteWords() } returns flowOf(fakeWords)

        repository.getSavedWords().test {
            assertEquals(SavedState.Loading, awaitItem())
            assertEquals(SavedState.Success(fakeWords), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isWordSaved emits loading and success`() = runTest {
        coEvery { favoriteWordDao.isFavorite("hello") } returns true
        repository.isWordSaved("hello").test {
            assertEquals(SavedState.Loading, awaitItem())
            assertEquals(SavedState.Success(true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleSavedWord inserts word when not saved`() = runTest {
        val wordInfo = WordInfo(
            word = "hello",
            phonetic = "həˈloʊ",
            meanings = listOf(
                Meaning(
                    partOfSpeech = "noun",
                    definitions = listOf(
                        Definition(
                            definition = "A greeting or expression of goodwill",
                            example = "She said 'hello' and waved.",
                            synonyms = listOf("hi", "hey", "greetings"),
                            antonyms = listOf("goodbye", "farewell")
                        )
                    )
                )
            )
        )
        coEvery { favoriteWordDao.isFavorite("hello") } returns false
        coEvery { favoriteWordDao.insertFavoriteWord(any()) } just Runs

        val result = repository.toggleSavedWord(wordInfo)

        assertEquals(SavedState.Success("Word saved to favorites"), result)
    }

    @Test
    fun `toggleSavedWord removes word when already saved`() = runTest {
        val wordInfo = WordInfo(
            word = "hello",
            phonetic = "həˈloʊ",
            meanings = listOf(
                Meaning(
                    partOfSpeech = "noun",
                    definitions = listOf(
                        Definition(
                            definition = "A greeting or expression of goodwill",
                            example = "She said 'hello' and waved.",
                            synonyms = listOf("hi", "hey", "greetings"),
                            antonyms = listOf("goodbye", "farewell")
                        )
                    )
                )
            )
        )

        coEvery { favoriteWordDao.isFavorite("hello") } returns true
        coEvery { favoriteWordDao.removeFavoriteWord("hello") } just Runs

        val result = repository.toggleSavedWord(wordInfo)

        assertEquals(SavedState.Success("Word removed from favorites"), result)
    }
}
