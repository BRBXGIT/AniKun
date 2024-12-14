package com.example.media_screen.media_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.media_screen.media_screen.screen.MediaDetailsScreen
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.media_screen.media_screen.screen.MediaScreenVM
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsScreenRoute(
    val mediaId: Int,
    val mediaType: String
)

fun NavGraphBuilder.mediaDetailsScreen(
    navController: NavController,
    userMangaLists: List<UserMangaLists>?,
    userAnimeLists: List<UserAnimeLists>?,
    userFavorites: Favourites?,
    favoritesScreenSharedVM: MediaFavoritesScreensSharedVM,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) = composable<MediaDetailsScreenRoute> {
    val mediaId = it.toRoute<MediaDetailsScreenRoute>().mediaId
    val mediaType = it.toRoute<MediaDetailsScreenRoute>().mediaType
    val mediaScreenVM = hiltViewModel<MediaScreenVM>()

    MediaDetailsScreen(
        mediaId = mediaId,
        viewModel = mediaScreenVM,
        navController = navController,
        userMangaLists = userMangaLists,
        userAnimeLists = userAnimeLists,
        userFavorites = userFavorites,
        mediaType = mediaType,
        favoritesScreenSharedVM = favoritesScreenSharedVM,
        profileScreenSharedVM = profileScreensSharedVM
    )
}