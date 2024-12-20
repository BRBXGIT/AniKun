package com.example.navbarscreens.trending_anime_screen.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.designsystem.anime_card.AnimeCard
import com.example.designsystem.error_section.ErrorSection
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeLCSection(
    anime: LazyPagingItems<AnimeListMedia>,
    navController: NavController
) {
    var errorText by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(anime.loadState.refresh) {
        if(anime.loadState.refresh is LoadState.Error) {
            errorText = (anime.loadState.refresh as LoadState.Error).error.message.toString()
        }
    }

    PullToRefreshBox(
        isRefreshing = (anime.loadState.refresh is LoadState.Loading),
        onRefresh = { anime.refresh() },
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if(errorText.isBlank()) {
                items(anime.itemCount) { index ->
                    val currentAnime = anime[index]

                    AnimeCard(
                        anime = currentAnime!!,
                        index = index,
                        onAnimeClick = {
                            navController.navigate(
                                MediaDetailsScreenRoute(
                                    mediaId = currentAnime.id,
                                    mediaType = currentAnime.type
                                )
                            )
                        }
                    )
                }
            } else {
                item {
                    ErrorSection(
                        errorText = errorText,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }
    }
}