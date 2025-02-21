package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dictionaryapp.R
import com.example.dictionaryapp.feature_dictionary.domain.model.WordInfo

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBackgroundColor = MaterialTheme.colorScheme.surface
    val scrollState = rememberScrollState()
    val isScrollable = scrollState.maxValue > 0
    var isBookmarked by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .width(340.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = 100.dp,
                    max = 600.dp
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                Text(
                    text = wordInfo.word,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                wordInfo.phonetic?.let { Text(text = it, fontWeight = FontWeight.Light) }
                Spacer(modifier = Modifier.height(16.dp))

                wordInfo.meanings.forEach { meaning ->
                    Text(text = meaning.partOfSpeech, fontWeight = FontWeight.Bold)
                    meaning.definitions.forEachIndexed { i, definition ->
                        Text(text = "${i + 1}. ${definition.definition}")
                        Spacer(modifier = Modifier.height(8.dp))
                        definition.example?.let { example ->
                            Text(text = "Example: $example")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            IconButton(
                onClick = { onToggleSave() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Image(
                    painter = painterResource(
                        id = if (isSaved) R.drawable.bookmark_filled
                        else R.drawable.bookmark_outlined
                    ),
                    contentDescription = if (isBookmarked) "Saved" else "Save word"
                )
            }
            if (isScrollable) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, cardBackgroundColor),
                                startY = 10f,
                                endY = 100f
                            )
                        )
                )
            }
        }
    }
}



