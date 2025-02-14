package com.example.dictionaryapp.feature_dictionary.presentation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(viewModel: WordInfoViewModel) {
    val suggestions by viewModel.suggestions
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = viewModel.searchQuery.value,
            onValueChange = { query ->
                Log.d("SearchBar", "User typed: $query")
                viewModel.onSearchQueryChanged(query)
                isDropdownExpanded = query.isNotEmpty()
                Log.d("SearchBar", "Dropdown expanded: $isDropdownExpanded")
            },
            modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
            placeholder = { Text(text = "Search...") }
        )

        Log.d("SearchBar", "Suggestions count: ${suggestions.size}")
        DropdownMenu(
            expanded = isDropdownExpanded && suggestions.isNotEmpty(),
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            suggestions.forEach { word ->
                Log.d("SearchBar", "Showing suggestion: $word")
                DropdownMenuItem(
                    text = { Text(word) },
                    onClick = {
                        viewModel.onSearch(word)
                        isDropdownExpanded = false
                    }
                )
            }
        }
    }
}



