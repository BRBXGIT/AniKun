package com.example.navbarscreens.trending_anime_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.navbarscreens.trending_anime_screen.screen.TrendingAnimeScreenVM
import com.example.navbarscreens.trending_anime_screen.screen.TrendingAnimeScreen
import kotlinx.serialization.Serializable
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia

@Serializable
data object AnimeScreenRoute

fun NavGraphBuilder.trendingAnimeScreen(
    navController: NavController,
    trendingAnime: LazyPagingItems<AnimeListMedia>,
    trendingAnimeScreenVM: TrendingAnimeScreenVM
) = composable<AnimeScreenRoute> {
    TrendingAnimeScreen(
        navController = navController,
        viewModel = trendingAnimeScreenVM,
        trendingAnime = trendingAnime
    )
}