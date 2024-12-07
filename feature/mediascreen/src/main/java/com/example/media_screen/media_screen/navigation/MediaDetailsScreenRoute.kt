package com.example.media_screen.media_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.media_screen.media_screen.screen.MediaDetailsScreen
import com.example.media_screen.media_screen.screen.MediaScreenVM
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsScreenRoute(
    val mediaId: Int
)

fun NavGraphBuilder.mediaDetailsScreen(
    navController: NavController,
    userMangaLists: List<UserMangaLists>?,
    userAnimeLists: List<UserAnimeLists>?
) = composable<MediaDetailsScreenRoute> {
    val mediaId = it.toRoute<MediaDetailsScreenRoute>().mediaId
    val mediaScreenVM = hiltViewModel<MediaScreenVM>()

    MediaDetailsScreen(
        mediaId = mediaId,
        viewModel = mediaScreenVM,
        navController = navController,
        userMangaLists = userMangaLists,
        userAnimeLists = userAnimeLists
    )
}