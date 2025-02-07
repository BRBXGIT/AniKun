package com.example.navbarscreens.profile_screen.sections

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
import com.example.data.remote.models.profile_models.user_anime_list_response.Entry
import com.example.designsystem.media_cards.AnimeProfileScreenCard
import com.example.designsystem.media_cards.MediaLongClickBS
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists

@Composable
fun UserAnimeLCSection(
    animeList: List<Entry>,
    navController: NavController,
    userAnimeLists: List<UserAnimeLists>,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(animeList) { index, anime ->
            var addToListBSOpen by rememberSaveable { mutableStateOf(false) }

            AnimeProfileScreenCard(
                anime = anime.media,
                progress = animeList[index].progress,
                index = index,
                onAnimeClick = {
                    navController.navigate(
                        MediaDetailsScreenRoute(
                            mediaId = anime.media.id,
                            mediaType = anime.media.type
                        )
                    )
                },
                onAnimeLongClick = { addToListBSOpen = true },
                onAddEpisodeClick = {  }
            )

            if(addToListBSOpen) {
                var userListType by rememberSaveable { mutableStateOf("") }
                userListType = checkIsMediaInUserList(emptyList(), userAnimeLists, anime.media.id)

                MediaLongClickBS(
                    onDismissRequest = { addToListBSOpen = false },
                    title = if(anime.media.title.english != null) anime.media.title.english!! else anime.media.title.romaji,
                    episodes = anime.media.episodes,
                    averageScore = anime.media.averageScore,
                    onListClick = { listType ->
                        addToListBSOpen = false
                        profileScreensSharedVM.changeMediaListType(anime.media.id, listType, anime.media.type)
                    },
                    mediaType = anime.media.type,
                    currentList = userListType
                )
            }
        }
    }
}