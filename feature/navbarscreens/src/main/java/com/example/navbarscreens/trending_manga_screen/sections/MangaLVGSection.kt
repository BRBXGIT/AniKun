package com.example.navbarscreens.trending_manga_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.common.check_functions.checkIsMediaInUserList
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.media_cards.MangaCard
import com.example.designsystem.media_cards.MediaLongClickBS
import com.example.designsystem.snackbars.SnackbarAction
import com.example.designsystem.snackbars.SnackbarController
import com.example.designsystem.snackbars.SnackbarEvent
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaLVGSection(
    manga: LazyPagingItems<MangaListMedia>,
    navController: NavController,
    userMangaLists: List<UserMangaLists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) {
    var errorText by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(manga.loadState.refresh) {
        if(manga.loadState.refresh is LoadState.Error) {
            errorText = (manga.loadState.refresh as LoadState.Error).error.message.toString()
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = "Something went wrong :(",
                    action = SnackbarAction(
                        name = "Refresh",
                        action = { manga.refresh() }
                    )
                )
            )
        }
    }

    PullToRefreshBox(
        isRefreshing = (manga.loadState.refresh is LoadState.Loading),
        onRefresh = { manga.refresh() },
        modifier = Modifier.fillMaxWidth()
    ) {
        if(manga.loadState.refresh !is LoadState.Error) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(manga.itemCount) { index ->
                    val currentManga = manga[index]

                    currentManga?.let {
                        var addToListBSOpen by rememberSaveable { mutableStateOf(false) }

                        MangaCard(
                            manga = currentManga,
                            index = index,
                            onMangaClick = {
                                navController.navigate(
                                    MediaDetailsScreenRoute(
                                        mediaId = currentManga.id,
                                        mediaType = currentManga.type
                                    )
                                )
                            },
                            onMangaLongClick = { addToListBSOpen = true }
                        )

                        if(addToListBSOpen) {
                            var userListType by rememberSaveable { mutableStateOf("") }
                            userListType = if(userMangaLists != null) {
                                checkIsMediaInUserList(userMangaLists, emptyList(), currentManga.id)
                            } else {
                                "Error :("
                            }

                            MediaLongClickBS(
                                onDismissRequest = { addToListBSOpen = false },
                                title = if(currentManga.title.english != null) currentManga.title.english!! else currentManga.title.romaji,
                                averageScore = currentManga.averageScore,
                                onListClick = { listType ->
                                    addToListBSOpen = false
                                    profileScreensSharedVM.changeMediaListType(currentManga.id, listType, currentManga.type)
                                },
                                mediaType = currentManga.type,
                                currentList = userListType
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                ErrorSection(
                    errorText = errorText,
                    modifier = Modifier
                )
            }
        }
    }
}