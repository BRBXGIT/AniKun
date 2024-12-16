package com.example.navbarscreens.trending_manga_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.navbarscreens.trending_manga_screen.screen.MangaScreen
import com.example.navbarscreens.trending_manga_screen.screen.TrendingMangaScreenVM
import kotlinx.serialization.Serializable
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

@Serializable
data object MangaScreenRoute

fun NavGraphBuilder.trendingMangaScreen(
    navController: NavController,
    trendingMangaScreenVM: TrendingMangaScreenVM,
    trendingManga: LazyPagingItems<MangaListMedia>
) = composable<MangaScreenRoute> {
    MangaScreen(
        viewModel = trendingMangaScreenVM,
        navController = navController,
        trendingManga = trendingManga
    )
}