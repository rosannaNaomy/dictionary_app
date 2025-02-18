package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex


@Composable
fun DropdownMenuOverlay(
    isDropdownExpanded: Boolean,
    suggestions: List<String>,
    onSearch: (String) -> Unit
) {
    if (isDropdownExpanded) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .absoluteOffset(y = 116.dp)
                .zIndex(2f)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(suggestions) { word ->
                        Text(
                            text = word,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSearch(word)
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}



