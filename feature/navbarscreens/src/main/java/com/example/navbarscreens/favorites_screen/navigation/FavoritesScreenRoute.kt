package com.example.navbarscreens.favorites_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.favorites_screen.screen.FavoritesScreen
import kotlinx.serialization.Serializable
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@Serializable
data object FavoritesScreenRoute

fun NavGraphBuilder.favoritesScreen(
    navController: NavController,
    favoritesException: String?,
    userFavorites: Favourites,
    mediaFavoritesScreensSharedVM: MediaFavoritesScreensSharedVM,
    userMangaLists: List<UserMangaLists>?,
    userAnimeLists: List<UserAnimeLists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM,
) = composable<FavoritesScreenRoute>(
    enterTransition = { fadeIn(tween((400))) },
    exitTransition = { fadeOut(tween(400)) }
) {
    FavoritesScreen(
        navController = navController,
        userFavorites = userFavorites,
        viewModel = mediaFavoritesScreensSharedVM,
        favoritesException = favoritesException,
        userMangaLists = userMangaLists,
        profileScreensSharedVM = profileScreensSharedVM,
        userAnimeLists = userAnimeLists
    )
}