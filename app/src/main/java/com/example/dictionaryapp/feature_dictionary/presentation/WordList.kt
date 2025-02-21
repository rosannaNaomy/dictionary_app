package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dictionaryapp.core.util.SavedState
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Composable
fun WordList(
    word: String,
    wordInfoItems: List<WordInfo>,
    isWordSavedState : SavedState<Boolean>,
    onToggleSave: (WordInfo) -> Unit,
    onCheckIfSaved: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$word · Results: ${wordInfoItems.size}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, bottom = 50.dp),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp
            ),
            horizontalArrangement = Arrangement.Center
        ) {
            items(wordInfoItems) { wordInfo ->
                LaunchedEffect(wordInfo.word) {
                    onCheckIfSaved(wordInfo.word)
                }
                val isSaved = isWordSavedState is SavedState.Success && isWordSavedState.data

                WordInfoItem(
                    wordInfo = wordInfo,
                    isSaved = isSaved,
                    onToggleSave = { onToggleSave(wordInfo) }
                )
            }
        }
    }
}


