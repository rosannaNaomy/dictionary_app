package com.example.dictionaryapp.feature_dictionary.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DictionaryScreen() {
    val viewModel: WordInfoViewModel = hiltViewModel()
    val state = viewModel.state.value // ✅ Uses updated ViewModel state
    val snackbarHostState = remember { SnackbarHostState() }

    // ✅ Collect UI events (such as error messages)
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
        MainContent(state = state, viewModel = viewModel)
    }
}

@Composable
fun MainContent(state: WordInfoState, viewModel: WordInfoViewModel) {
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(viewModel = viewModel) // ✅ Search input at the top
            Spacer(modifier = Modifier.height(16.dp))

            when {
                state.isLoading -> {
                    // ✅ Show a loading spinner while fetching data
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.wordInfoItems.isEmpty() && state.isLoading.not() -> {
                    // ✅ Show "No results found" if no words are available
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No results found.", color = Color.Gray)
                    }
                }
                else -> {
                    // ✅ Show the list of word definitions
                    WordList(wordInfoItems = state.wordInfoItems)
                }
            }
        }
    }
}

//@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun DictionaryScreen() {
//    val viewModel: WordInfoViewModel = hiltViewModel()
//    val state = viewModel.state.value
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    LaunchedEffect(key1 = true) {
//        viewModel.eventFlow.collectLatest { event ->
//            when (event) {
//                is WordInfoViewModel.UIEvent.ShowSnackBar -> {
//                    snackbarHostState.showSnackbar(
//                        message = event.message
//                    )
//                }
//            }
//        }
//    }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) {
//        MainContent(state = state, viewModel = viewModel)
//    }
//}
//
//@Composable
//fun MainContent(state: WordInfoState, viewModel: WordInfoViewModel) {
//    Box(
//        modifier = Modifier
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            SearchBar(viewModel = viewModel)
//            Spacer(modifier = Modifier.height(16.dp))
//            WordList(wordInfoItems = state.wordInfoItems)
//        }
//
//        if (state.isLoading) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//        }
//    }
//}

