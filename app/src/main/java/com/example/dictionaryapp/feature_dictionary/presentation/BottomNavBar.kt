package com.example.dictionaryapp.feature_dictionary.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.dictionaryapp.R


@Composable
fun BottomNavBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf("Search", "Saved")

    NavigationBar {
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                icon = {
                    if (index == 0) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = label)
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark_filled_blk),
                            contentDescription = label
                        )
                    }
                },
                label = { Text(label) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

