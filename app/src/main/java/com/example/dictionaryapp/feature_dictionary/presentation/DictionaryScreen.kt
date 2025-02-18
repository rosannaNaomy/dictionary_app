package com.example.dictionaryapp.feature_dictionary.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var selectedTab by remember { mutableStateOf(0) }

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
        bottomBar = {
            BottomNavBar(selectedItem = selectedTab) { index ->
                selectedTab = index
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (selectedTab == 0) {
                MainContent(
                    state = state,
                    searchQuery = searchQuery,
                    suggestions = suggestions,
                    onSearchQueryChanged = viewModel::onSearchQueryChanged,
                    onSearch = viewModel::onSearch
                )
            } else {
                SavedWordsScreen()
            }
        }
    }
}


