package com.example.navbarscreens.manga_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbarscreens.manga_screen.screen.MangaScreen
import com.example.navbarscreens.manga_screen.screen.MangaScreenVM
import kotlinx.serialization.Serializable

@Serializable
object MangaScreenRoute

fun NavGraphBuilder.mangaScreen(
    navController: NavController
) = composable<MangaScreenRoute> {
    val mangaScreenVM = hiltViewModel<MangaScreenVM>()

    MangaScreen(
        viewModel = mangaScreenVM,
        navController = navController
    )
}