package com.example.dictionaryapp.feature_dictionary.presentation

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
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(viewModel: WordInfoViewModel) {
    val suggestions by viewModel.suggestions // ✅ Observe suggestions state
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = viewModel.searchQuery.value,
            onValueChange = { query ->
                viewModel.onSearchQueryChanged(query) // ✅ Fetch suggestions
                isDropdownExpanded = query.isNotEmpty() // ✅ Show dropdown when typing
            },
            modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
            placeholder = { Text(text = "Search...") }
        )

        DropdownMenu(
            expanded = isDropdownExpanded && suggestions.isNotEmpty(),
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            suggestions.forEach { word ->
                DropdownMenuItem(
                    text = { Text(word) }, // ✅ Explicitly pass text
                    onClick = {
                        viewModel.onSearch(word) // ✅ Fetch word definition when selected
                        isDropdownExpanded = false // ✅ Close dropdown
                    }
                )
            }
        }
    }
}

//@Composable
//fun SearchBar(viewModel: WordInfoViewModel) {
//    TextField(
//        value = viewModel.searchQuery.value,
//        onValueChange = viewModel::onSearch,
//        modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
//        placeholder = { Text(text = "Search...") }
//    )
//}

