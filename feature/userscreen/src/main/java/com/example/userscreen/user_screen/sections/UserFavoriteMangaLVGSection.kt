package com.example.userscreen.user_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.check_functions.checkIsMangaInUserLists
import com.example.common.check_functions.checkIsMediaInUserList
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists
import com.example.designsystem.media_cards.MangaCard
import com.example.designsystem.media_cards.MediaLongClickBS
import com.example.designsystem.sections.EmptyContentSection
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

@Composable
fun UserFavoriteMangaLVGSection(
    userName: String,
    favoriteManga: List<MangaListMedia>,
    navController: NavController,
    userMangaLists: List<Lists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM,
    bottomPadding: Dp
) {
    if(favoriteManga.isNotEmpty()) {
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
            itemsIndexed(favoriteManga) { index, manga ->
                val userListTypeMangaIn = checkIsMangaInUserLists(userMangaLists, manga.id)
                var addToListBSOpen by rememberSaveable { mutableStateOf(false) }

                MangaCard(
                    manga = manga,
                    index = index,
                    onMangaClick = {
                        navController.navigate(
                            MediaDetailsScreenRoute(
                                mediaId = manga.id,
                                mediaType = manga.type
                            )
                        )
                    },
                    onMangaLongClick = { addToListBSOpen = true },
                    listType = userListTypeMangaIn
                )

                if(addToListBSOpen) {
                    var userListType by rememberSaveable { mutableStateOf("") }
                    userListType = if(userMangaLists != null) {
                        checkIsMediaInUserList(userMangaLists, emptyList(), manga.id)
                    } else {
                        "Error :("
                    }

                    MediaLongClickBS(
                        onDismissRequest = { addToListBSOpen = false },
                        title = if(manga.title.english != null) manga.title.english!! else manga.title.romaji,
                        averageScore = manga.averageScore,
                        onListClick = { listType ->
                            addToListBSOpen = false
                            profileScreensSharedVM.changeMediaListType(manga.id, listType, manga.type)
                        },
                        mediaType = manga.type,
                        currentList = userListType
                    )
                }
            }

            //Use two items cause of grid
            item {
                Spacer(
                    modifier = Modifier.height(bottomPadding)
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(bottomPadding)
                )
            }
        }
    } else {
        EmptyContentSection(
            text = "Hmm, $userName doesn't have any favorite manga",
            modifier = Modifier.fillMaxSize()
        )
    }
}