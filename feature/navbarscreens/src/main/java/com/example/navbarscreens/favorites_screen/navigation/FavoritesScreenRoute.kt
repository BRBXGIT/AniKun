package com.example.navbarscreens.favorites_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.navbarscreens.favorites_screen.screen.FavoritesScreen
import kotlinx.serialization.Serializable

@Serializable
data object FavoritesScreenRoute

fun NavGraphBuilder.favoritesScreen(
    navController: NavController,
    favoritesException: String?,
    userFavorites: Favourites,
    mediaFavoritesScreensSharedVM: MediaFavoritesScreensSharedVM,
) = composable<FavoritesScreenRoute> {
    FavoritesScreen(
        navController = navController,
        userFavorites = userFavorites,
        viewModel = mediaFavoritesScreensSharedVM,
        favoritesException = favoritesException
    )
}