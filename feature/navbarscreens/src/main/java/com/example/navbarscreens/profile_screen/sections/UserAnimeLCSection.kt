package com.example.navbarscreens.profile_screen.sections

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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.designsystem.anime_card.AnimeCard
import com.example.designsystem.error_section.ErrorSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAnimeLCSection(
    userAnime: LazyPagingItems<UserAnimeListMedia>
) {
    var errorText by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(userAnime.loadState.refresh) {
        if(userAnime.loadState.refresh is LoadState.Error) {
            errorText = (userAnime.loadState.refresh as LoadState.Error).error.message.toString()
        }
    }

    PullToRefreshBox(
        isRefreshing = (userAnime.loadState.refresh is LoadState.Loading),
        onRefresh = { userAnime.refresh() },
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if(errorText.isBlank()) {
                items(userAnime.itemCount) { index ->
                    val currentAnime = userAnime[index]

                    AnimeCard(
                        anime = currentAnime!!.media,
                        index = index
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