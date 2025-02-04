package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(viewModel: WordInfoViewModel) {
    TextField(
        value = viewModel.searchQuery.value,
        onValueChange = viewModel::onSearch,
        modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
        placeholder = { Text(text = "Search...") }
    )
}

