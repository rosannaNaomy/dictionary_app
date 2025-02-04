package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Composable
fun WordList(wordInfoItems: List<WordInfo>) {
    if (wordInfoItems.isEmpty()) {
        // ✅ Show a message when there are no results
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No results found.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp) // ✅ Consistent spacing
        ) {
            items(wordInfoItems.size) { index ->  // ✅ Correctly iterating with index
                val wordInfo = wordInfoItems[index]
                WordInfoItem(wordInfo = wordInfo)
                Divider()
            }
        }

    }
}

//@Composable
//fun WordList(wordInfoItems: List<WordInfo>) {
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(wordInfoItems.size) { i ->
//            val wordInfo = wordInfoItems[i]
//            if (i > 0) {
//                Spacer(modifier = Modifier.height(8.dp))
//            }
//            WordInfoItem(wordInfo = wordInfo)  // Passing the WordInfo data to WordInfoItem composable
//            if (i < wordInfoItems.size - 1) {
//                Divider()
//            }
//        }
//    }
//}

