package com.example.navbarscreens.favorites_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.data.remote.models.profile_models.user_favorites_response.UserFavoritesResponse
import com.example.navbarscreens.favorites_screen.screen.FavoritesScreen
import com.example.navbarscreens.favorites_screen.screen.FavoritesScreenVM
import kotlinx.serialization.Serializable

@Serializable
object FavoritesScreenRoute

fun NavGraphBuilder.favoritesScreen(
    navController: NavController,
    userFavorites: UserFavoritesResponse,
    favoritesScreenVM: FavoritesScreenVM,
    chosenContentType: Boolean
) = composable<FavoritesScreenRoute> {
    FavoritesScreen(
        navController = navController,
        userFavorites = userFavorites,
        viewModel = favoritesScreenVM,
        chosenContentType = chosenContentType
    )
}