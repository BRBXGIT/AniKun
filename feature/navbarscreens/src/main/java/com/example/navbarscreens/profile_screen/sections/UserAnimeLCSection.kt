package com.example.navbarscreens.profile_screen.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_anime_list_response.Entry
import com.example.designsystem.anime_card.AnimeCard
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@Composable
fun UserAnimeLCSection(
    animeList: List<Entry>,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(animeList) { index, anime ->
            AnimeCard(
                anime = anime.media,
                index = index,
                onAnimeClick = {
                    navController.navigate(
                        MediaDetailsScreenRoute(
                            mediaId = anime.media.id,
                            mediaType = anime.media.type
                        )
                    )
                }
            )
        }
    }
}