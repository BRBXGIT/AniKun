package com.example.navbarscreens.trending_anime_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.trending_anime_screen.screen.TrendingAnimeScreen
import com.example.navbarscreens.trending_anime_screen.screen.TrendingAnimeScreenVM
import kotlinx.serialization.Serializable
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists

@Serializable
data object AnimeScreenRoute

fun NavGraphBuilder.trendingAnimeScreen(
    navController: NavController,
    trendingAnime: LazyPagingItems<AnimeListMedia>,
    trendingAnimeScreenVM: TrendingAnimeScreenVM,
    userAnimeLists: List<UserAnimeLists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) = composable<AnimeScreenRoute>(
    enterTransition = { fadeIn(tween((400))) },
    exitTransition = { fadeOut(tween(400)) }
) {
    TrendingAnimeScreen(
        navController = navController,
        viewModel = trendingAnimeScreenVM,
        trendingAnime = trendingAnime,
        userAnimeLists = userAnimeLists,
        profileScreensSharedVM = profileScreensSharedVM
    )
}