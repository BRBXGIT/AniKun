package com.example.anilist

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.navbarscreens.anime_screen.navigation.AnimeScreenRoute
import com.example.navbarscreens.anime_screen.navigation.animeScreen
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM
import com.example.navbarscreens.common.navigation.NavBarScreensRoute
import com.example.navbarscreens.manga_screen.navigation.mangaScreen
import com.example.navbarscreens.manga_screen.screen.MangaScreenVM
import com.example.navbarscreens.profile_screen.navigation.profileScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    //Initialize values here to don't refresh it after screen will be recomposed
    val animeScreenVM = hiltViewModel<AnimeScreenVM>()
    val mangaScreenVM = hiltViewModel<MangaScreenVM>()

    val trendingAnime = animeScreenVM.trendingAnime.collectAsLazyPagingItems()
    val thisSeasonAnime = animeScreenVM.thisSeasonAnime.collectAsLazyPagingItems()
    val nextSeasonAnime = animeScreenVM.nextSeasonAnime.collectAsLazyPagingItems()
    val allTimePopularAnime = animeScreenVM.allTimePopularAnime.collectAsLazyPagingItems()

    val trendingManga = mangaScreenVM.trendingManga.collectAsLazyPagingItems()
    val allTimePopularManga = mangaScreenVM.allTimePopularManga.collectAsLazyPagingItems()
    val popularManhwa = mangaScreenVM.popularManhwa.collectAsLazyPagingItems()

    NavHost(
        navController = navController,
        startDestination = NavBarScreensRoute
    ) {
        navigation<NavBarScreensRoute>(
            startDestination = AnimeScreenRoute
        ) {
            animeScreen(
                navController = navController,
                trendingAnime = trendingAnime,
                thisSeasonAnime = thisSeasonAnime,
                nextSeasonAnime = nextSeasonAnime,
                allTimePopularAnime = allTimePopularAnime,
                animeScreenVM = animeScreenVM
            )

            mangaScreen(
                navController = navController,
                mangaScreenVM = mangaScreenVM,
                trendingManga = trendingManga,
                allTimePopularManga = allTimePopularManga,
                popularManhwa = popularManhwa
            )

            profileScreen(navController)
        }
    }
}