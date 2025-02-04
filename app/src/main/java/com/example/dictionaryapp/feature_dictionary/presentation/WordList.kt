package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo


@Composable
fun WordList(wordInfoItems: List<WordInfo>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(wordInfoItems.size) { i ->
            val wordInfo = wordInfoItems[i]
            if (i > 0) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            WordInfoItem(wordInfo = wordInfo)  // Passing the WordInfo data to WordInfoItem composable
            if (i < wordInfoItems.size - 1) {
                Divider()
            }
        }
    }
}

