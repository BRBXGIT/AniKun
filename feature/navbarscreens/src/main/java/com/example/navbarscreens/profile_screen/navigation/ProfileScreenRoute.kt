package com.example.navbarscreens.profile_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia
import com.example.navbarscreens.profile_screen.screen.ProfileScreen
import com.example.navbarscreens.profile_screen.screen.ProfileScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenRoute

fun NavGraphBuilder.profileScreen(
    navController: NavController,
    profileScreenVM: ProfileScreenVM,
    aniListUser: AniListUser,
    chosenContentType: Boolean,
    userAnimeLists: List<LazyPagingItems<UserAnimeListMedia>>,
    userMangaLists: List<LazyPagingItems<UserMangaListMedia>>
) = composable<ProfileScreenRoute> {
    ProfileScreen(
        navController = navController,
        viewModel = profileScreenVM,
        aniListUser = aniListUser,
        chosenContentType = chosenContentType,
        userAnimeLists = userAnimeLists,
        userMangaLists = userMangaLists
    )
}