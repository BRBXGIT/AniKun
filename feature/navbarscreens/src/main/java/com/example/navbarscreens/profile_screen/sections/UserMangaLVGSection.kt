package com.example.navbarscreens.profile_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.common.check_functions.checkIsMediaInUserList
import com.example.designsystem.media_cards.MangaCard
import com.example.designsystem.media_cards.MediaLongClickBS
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.data.remote.models.profile_models.user_manga_list_response.Entry as MangaListEntry
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@Composable
fun UserMangaLVGSection(
    mangaList: List<MangaListEntry>,
    navController: NavController,
    profileScreensSharedVM: MediaProfileScreensSharedVM,
    userMangaLists: List<UserMangaLists>
) {
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
        itemsIndexed(mangaList) { index, manga ->
            var addToListBSOpen by rememberSaveable { mutableStateOf(false) }

            MangaCard(
                manga = manga.media,
                index = index,
                onMangaClick = {
                    navController.navigate(
                        MediaDetailsScreenRoute(
                            mediaId = manga.media.id,
                            mediaType = manga.media.type
                        )
                    )
                },
                onMangaLongClick = { addToListBSOpen = true }
            )

            if(addToListBSOpen) {
                var userListType by rememberSaveable { mutableStateOf("") }
                userListType = checkIsMediaInUserList(userMangaLists, emptyList(), manga.media.id)

                MediaLongClickBS(
                    onDismissRequest = { addToListBSOpen = false },
                    title = if(manga.media.title.english != null) manga.media.title.english!! else manga.media.title.romaji,
                    averageScore = manga.media.averageScore,
                    onListClick = { listType ->
                        addToListBSOpen = false
                        profileScreensSharedVM.changeMediaListType(manga.media.id, listType, manga.media.type)
                    },
                    mediaType = manga.media.type,
                    currentList = userListType
                )
            }
        }
    }
}