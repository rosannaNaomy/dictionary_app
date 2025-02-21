package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Composable
fun MainContent(
    state: WordInfoState,
    searchQuery: String,
    suggestions: List<String>,
    isWordSavedState: SavedState<Boolean>,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onToggleSave: (WordInfo) -> Unit,
    onCheckIfSaved: (String) -> Unit
) {
    var isDropdownExpanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTapGestures { isDropdownExpanded = false }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onQueryChanged = { query ->
                    onSearchQueryChanged(query)
                    isDropdownExpanded = query.isNotEmpty() && suggestions.isNotEmpty()
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            DisplayWordList(
                state,
                isWordSavedState,
                onToggleSave,
                onCheckIfSaved
            )
        }

        DropdownMenuOverlay(
            isDropdownExpanded = isDropdownExpanded,
            suggestions = suggestions,
            onSearch = {
                isDropdownExpanded = false
                onSearch(it)
            }
        )
    }
}
