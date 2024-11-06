package com.example.navbarscreens.anime_screen.sections

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.anime_models.response.Media

@Composable
fun AnimeLCSection(
    anime: LazyPagingItems<Media>
) {
    LazyColumn {
        items(anime.itemCount) { index ->
            val currentAnime = anime[index]

            currentAnime?.let {
                Text(currentAnime.title.english.toString())
            }
        }
    }
}