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
import com.example.common.check_functions.checkIsMediaInUserList
import com.example.designsystem.sections.ErrorSection
import com.example.designsystem.media_cards.AnimeCard
import com.example.designsystem.media_cards.MediaLongClickBS
import com.example.designsystem.snackbars.SnackbarAction
import com.example.designsystem.snackbars.SnackbarController
import com.example.designsystem.snackbars.SnackbarEvent
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeLCSection(
    anime: LazyPagingItems<AnimeListMedia>,
    userAnimeLists: List<UserAnimeLists>?,
    navController: NavController,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) {
    var errorText by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(anime.loadState.refresh) {
        if(anime.loadState.refresh is LoadState.Error) {
            errorText = (anime.loadState.refresh as LoadState.Error).error.message.toString()
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = "Something went wrong :(",
                    action = SnackbarAction(
                        name = "Refresh",
                        action = { anime.refresh() }
                    )
                )
            )
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
            if(anime.loadState.refresh !is LoadState.Error) {
                items(anime.itemCount) { index ->
                    val currentAnime = anime[index]

                    currentAnime?.let {
                        var addToListBSOpen by rememberSaveable { mutableStateOf(false) }

                        AnimeCard(
                            anime = currentAnime,
                            index = index,
                            onAnimeClick = {
                                navController.navigate(
                                    MediaDetailsScreenRoute(
                                        mediaId = currentAnime.id,
                                        mediaType = currentAnime.type
                                    )
                                )
                            },
                            onAnimeLongClick = { addToListBSOpen = true }
                        )

                        if(addToListBSOpen) {
                            var userListType by rememberSaveable { mutableStateOf("") }
                            userListType = if(userAnimeLists != null) {
                                checkIsMediaInUserList(emptyList(), userAnimeLists, currentAnime.id)
                            } else {
                                "Error :("
                            }

                            MediaLongClickBS(
                                onDismissRequest = { addToListBSOpen = false },
                                title = if(currentAnime.title.english != null) currentAnime.title.english!! else currentAnime.title.romaji,
                                episodes = currentAnime.episodes,
                                averageScore = currentAnime.averageScore,
                                onListClick = { listType ->
                                    addToListBSOpen = false
                                    profileScreensSharedVM.changeMediaListType(currentAnime.id, listType, currentAnime.type)
                                },
                                mediaType = currentAnime.type,
                                currentList = userListType
                            )
                        }
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