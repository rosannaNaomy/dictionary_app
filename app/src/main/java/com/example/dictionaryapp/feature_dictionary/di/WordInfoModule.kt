package com.example.dictionaryapp.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dictionaryapp.BuildConfig
import com.example.dictionaryapp.feature_dictionary.data.local.FavoriteWordDao
import com.example.dictionaryapp.feature_dictionary.data.local.WordInfoDatabase
import com.example.dictionaryapp.feature_dictionary.data.local.converters.Converters
import com.example.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapp.feature_dictionary.data.remote.openai.OpenAIService
import com.example.dictionaryapp.feature_dictionary.data.repository.SavedRepositoryImpl
import com.example.dictionaryapp.feature_dictionary.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.feature_dictionary.data.util.MoshiParser
import com.example.dictionaryapp.feature_dictionary.domain.repository.SavedRepository
import com.example.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetSavedWordStatus
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetSavedWords
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetSuggestedWords
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetToggleSavedWordStatus
import com.example.dictionaryapp.feature_dictionary.domain.use_cases.GetWordInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {

    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: WordInfoRepository): GetWordInfo {
        return GetWordInfo(repository)
    }

    @Provides
    @Singleton
    fun provideMoshiParser(): MoshiParser {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return MoshiParser(moshi)
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application, moshiParser: MoshiParser): WordInfoDatabase {
        return Room.databaseBuilder(
            app, WordInfoDatabase::class.java, "word_db"
        ).addTypeConverter(Converters(moshiParser))
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteWordDao(db: WordInfoDatabase): FavoriteWordDao {
        return db.favoriteWordDao()
    }

    @Provides
    @Singleton
    fun provideDictionaryApi(moshiParser: MoshiParser): DictionaryApi {
        return Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev")
            .addConverterFactory(MoshiConverterFactory.create(moshiParser.moshi))
            .build()
            .create(DictionaryApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenAIService(moshiParser: MoshiParser): OpenAIService {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create(moshiParser.moshi))
            .build()
            .create(OpenAIService::class.java)
    }


    private const val APIKEY = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideWordRepository(
        db: WordInfoDatabase,
        api: DictionaryApi,
        openAIService: OpenAIService
    ): WordInfoRepository {
        val apiKey = APIKEY
        return WordInfoRepositoryImpl(
            api,
            openAIService,
            db.wordInfoDao(),
            db.wordSuggestionDao(),
            apiKey
        )
    }

    @Provides
    @Singleton
    fun provideSavedRepository(favoriteWordDao: FavoriteWordDao): SavedRepository {
        return SavedRepositoryImpl(favoriteWordDao)
    }

    @Provides
    @Singleton
    fun provideGetSuggestedWordsUseCase(repository: WordInfoRepository): GetSuggestedWords {
        return GetSuggestedWords(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedWords(repository: SavedRepository): GetSavedWords {
        return GetSavedWords(repository)
    }

    @Provides
    @Singleton
    fun provideGetSavedWordStatus(repository: SavedRepository): GetSavedWordStatus {
        return GetSavedWordStatus(repository)
    }

    @Provides
    @Singleton
    fun provideGetToggleSavedWordStatus(repository: SavedRepository): GetToggleSavedWordStatus {
        return GetToggleSavedWordStatus(repository)
    }


}