package com.example.dictionaryapp.feature_dictionary.presentation.saved_words

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SavedWordsScreen() {
    val savedViewModel: SavedViewModel = hiltViewModel()
    val savedWordsState by savedViewModel.savedWordsState.collectAsState()

    DisplaySavedList(
        savedWordsState = savedWordsState,
        onToggleSave = savedViewModel::toggleWord
    )
}
