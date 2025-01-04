package com.example.userscreen.user_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.userscreen.user_screen.screen.UserScreen
import com.example.userscreen.user_screen.screen.UserScreenVM
import kotlinx.serialization.Serializable
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@Serializable
data class UserScreenRoute(
    val userName: String
)

fun NavGraphBuilder.userScreen(
    navController: NavController,
    profileScreensSharedVM: MediaProfileScreensSharedVM,
    userMangaLists: List<UserMangaLists>?,
    userAnimeLists: List<UserAnimeLists>?,
) = composable<UserScreenRoute> {
    val userName = it.toRoute<UserScreenRoute>().userName
    val userScreenVM = hiltViewModel<UserScreenVM>()

    UserScreen(
        userName = userName,
        viewModel = userScreenVM,
        navController = navController,
        profileScreensSharedVM = profileScreensSharedVM,
        userMangaLists = userMangaLists,
        userAnimeLists = userAnimeLists
    )
}