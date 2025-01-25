package com.example.navbarscreens.profile_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.profile_screen.screen.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenRoute

fun NavGraphBuilder.profileScreen(
    navController: NavController,
    mediaProfileScreensSharedVM: MediaProfileScreensSharedVM,
    aniListUser: AniListUser,
    chosenContentType: Boolean,
    userAnimeLists: UserAnimeListsResponse,
    userMangaLists: UserMangaListsResponse
) = composable<ProfileScreenRoute>(
    enterTransition = { fadeIn(tween((400))) },
    exitTransition = { fadeOut(tween(400)) }
) {
    ProfileScreen(
        navController = navController,
        viewModel = mediaProfileScreensSharedVM,
        aniListUser = aniListUser,
        chosenContentType = chosenContentType,
        userAnimeLists = userAnimeLists,
        userMangaLists = userMangaLists
    )
}