package com.example.navbarscreens.anime_screen.sections

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.anime_models.response.Media
import com.example.designsystem.anime_card.AnimeCard

@Composable
fun AnimeLCSection(
    anime: LazyPagingItems<Media>
) {
    LazyColumn {
        items(anime.itemCount) { index ->
            val currentAnime = anime[index]

            currentAnime?.let {
                AnimeCard(
                    posterPath = currentAnime.coverImage.large,
                    title = if(currentAnime.title.english == null) currentAnime.title.romaji else currentAnime.title.english!!,
                    description = currentAnime.description,
                    episodes = currentAnime.episodes,
                    averageScore = currentAnime.averageScore.toString()
                )
            }
        }
    }
}