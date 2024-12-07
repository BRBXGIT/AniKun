package com.example.navbarscreens.favorites_screen.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.designsystem.anime_card.AnimeCard
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@Composable
fun FavoriteAnimeLCSection(
    favoriteAnime: List<AnimeListMedia>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(favoriteAnime) { index, anime ->
            AnimeCard(
                anime = anime,
                index = index,
                onAnimeClick = {
                    navController.navigate(
                        MediaDetailsScreenRoute(
                            mediaId = anime.id,
                            mediaType = anime.type
                        )
                    )
                }
            )
        }
    }
}