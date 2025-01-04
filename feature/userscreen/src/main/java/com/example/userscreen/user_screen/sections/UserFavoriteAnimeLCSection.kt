package com.example.userscreen.user_screen.sections

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.common.check_functions.checkIsMediaInUserList
import com.example.data.remote.models.anime_list_response.Media
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists
import com.example.designsystem.media_cards.AnimeCard
import com.example.designsystem.media_cards.MediaLongClickBS
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM

@Composable
fun UserFavoriteAnimeLCSection(
    favoriteAnime: List<Media>,
    navController: NavController,
    userAnimeLists: List<Lists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(favoriteAnime) { index, anime ->
            var addToListBSOpen by rememberSaveable { mutableStateOf(false) }

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
                },
                onAnimeLongClick = { addToListBSOpen = true }
            )

            if(addToListBSOpen) {
                var userListType by rememberSaveable { mutableStateOf("") }
                userListType = if(userAnimeLists != null) {
                    checkIsMediaInUserList(emptyList(), userAnimeLists, anime.id)
                } else {
                    "Error :("
                }

                MediaLongClickBS(
                    onDismissRequest = { addToListBSOpen = false },
                    title = if(anime.title.english != null) anime.title.english!! else anime.title.romaji,
                    episodes = anime.episodes,
                    averageScore = anime.averageScore,
                    onListClick = { listType ->
                        addToListBSOpen = false
                        profileScreensSharedVM.changeMediaListType(anime.id, listType, anime.type)
                    },
                    mediaType = anime.type,
                    currentList = userListType
                )
            }
        }
    }
}