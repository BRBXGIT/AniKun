package com.example.navbarscreens.anime_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbarscreens.anime_screen.screen.AnimeScreen
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM
import kotlinx.serialization.Serializable

@Serializable
object AnimeScreenRoute

fun NavGraphBuilder.animeScreen(
    navController: NavController
) = composable<AnimeScreenRoute> {
    val animeScreenVM = hiltViewModel<AnimeScreenVM>()

    AnimeScreen(
        navController = navController,
        viewModel = animeScreenVM
    )
}