package com.example.navbarscreens.favorites_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.navbarscreens.favorites_screen.screen.FavoritesScreen
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import kotlinx.serialization.Serializable

@Serializable
object FavoritesScreenRoute

fun NavGraphBuilder.favoritesScreen(
    navController: NavController,
    favoritesException: String?,
    userFavorites: Favourites,
    mediaFavoritesScreensSharedVM: MediaFavoritesScreensSharedVM,
    chosenContentType: Boolean
) = composable<FavoritesScreenRoute> {
    FavoritesScreen(
        navController = navController,
        userFavorites = userFavorites,
        viewModel = mediaFavoritesScreensSharedVM,
        chosenContentType = chosenContentType,
        favoritesException = favoritesException
    )
}