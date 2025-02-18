package com.example.dictionaryapp.feature_dictionary.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DictionaryScreen() {
    val viewModel: WordInfoViewModel = hiltViewModel()
    val state = viewModel.state.value
    val searchQuery by viewModel.searchQuery
    val suggestions by viewModel.suggestions
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is WordInfoViewModel.UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        MainContent(
            state = state,
            searchQuery = searchQuery,
            suggestions = suggestions,
            onSearchQueryChanged = viewModel::onSearchQueryChanged,
            onSearch = viewModel::onSearch
        )
    }
}


