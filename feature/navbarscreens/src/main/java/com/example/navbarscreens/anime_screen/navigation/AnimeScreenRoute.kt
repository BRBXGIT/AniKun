package com.example.navbarscreens.anime_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.navbarscreens.anime_screen.screen.AnimeScreen
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object AnimeScreenRoute

fun NavGraphBuilder.animeScreen(
    navController: NavController,
    trendingAnime: LazyPagingItems<AnimeListMedia>,
    animeScreenVM: AnimeScreenVM
) = composable<AnimeScreenRoute> {
    AnimeScreen(
        navController = navController,
        viewModel = animeScreenVM,
        trendingAnime = trendingAnime
    )
}