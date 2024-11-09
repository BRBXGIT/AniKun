package com.example.navbarscreens.anime_screen.sections

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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.anime_models.anime_list_response.Media
import com.example.designsystem.anime_card.AnimeCard
import com.example.designsystem.error_section.ErrorSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeLCSection(
    anime: LazyPagingItems<Media>
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
        LazyColumn {
            if(errorText.isBlank()) {
                items(anime.itemCount) { index ->
                    val currentAnime = anime[index]

                    currentAnime?.let {
                        AnimeCard(
                            posterPath = currentAnime.coverImage.large,
                            title = if(currentAnime.title.english == null) currentAnime.title.romaji else currentAnime.title.english!!,
                            description = currentAnime.description.toString(),
                            episodes = currentAnime.episodes,
                            averageScore = currentAnime.averageScore.toString(),
                            index = index
                        )
                    }
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