package com.example.navbarscreens.trending_manga_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.trending_manga_screen.screen.MangaScreen
import com.example.navbarscreens.trending_manga_screen.screen.TrendingMangaScreenVM
import kotlinx.serialization.Serializable
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@Serializable
data object MangaScreenRoute

fun NavGraphBuilder.trendingMangaScreen(
    navController: NavController,
    trendingMangaScreenVM: TrendingMangaScreenVM,
    trendingManga: LazyPagingItems<MangaListMedia>,
    userMangaLists: List<UserMangaLists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) = composable<MangaScreenRoute> {
    MangaScreen(
        viewModel = trendingMangaScreenVM,
        navController = navController,
        trendingManga = trendingManga,
        userMangaLists = userMangaLists,
        profileScreensSharedVM = profileScreensSharedVM
    )
}