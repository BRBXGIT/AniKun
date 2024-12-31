package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.media_cards.AnimeCard
import com.example.designsystem.media_cards.MangaCard
import com.example.designsystem.theme.mShapes
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaScreenVM
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaListByGenreBS(
    viewModel: MediaScreenVM,
    mediaType: String,
    navController: NavController,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = mShapes.small,
    ) {
        if(mediaType == "ANIME") {
            val animeByGenre = viewModel.animeByGenre.collectAsLazyPagingItems()

            AnimeLC(
                anime = animeByGenre,
                navController = navController
            )
        } else {
            val mangaByGenre = viewModel.mangaByGenre.collectAsLazyPagingItems()

            MangaLVG(
                manga = mangaByGenre,
                navController = navController
            )
        }
    }
}

@Composable
private fun AnimeLC(
    anime: LazyPagingItems<AnimeListMedia>,
    navController: NavController
) {
    var errorText by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(anime.loadState.refresh) {
        if(anime.loadState.refresh is LoadState.Error) {
            errorText = (anime.loadState.refresh as LoadState.Error).error.message.toString()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        if(errorText.isBlank()) {
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

@Composable
private fun MangaLVG(
    manga: LazyPagingItems<MangaListMedia>,
    navController: NavController
) {
    var errorText by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(manga.loadState.refresh) {
        if(manga.loadState.refresh is LoadState.Error) {
            errorText = (manga.loadState.refresh as LoadState.Error).error.message.toString()
        }
    }

    if(errorText.isBlank()) {
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