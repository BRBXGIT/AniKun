package com.example.anilist

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.auth.navigation.AuthScreenRoute
import com.example.auth.navigation.authScreen
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.navbarscreens.anime_screen.navigation.AnimeScreenRoute
import com.example.navbarscreens.anime_screen.navigation.animeScreen
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM
import com.example.navbarscreens.common.navigation.NavBarScreensRoute
import com.example.navbarscreens.manga_screen.navigation.mangaScreen
import com.example.navbarscreens.manga_screen.screen.MangaScreenVM
import com.example.navbarscreens.profile_screen.navigation.profileScreen
import com.example.navbarscreens.profile_screen.screen.ProfileScreenVM

@Composable
fun NavGraph(
    prefs: SharedPreferences
) {
    val navController = rememberNavController()

    //Initialize values here to don't refresh it after screen will be recomposed
    val animeScreenVM = hiltViewModel<AnimeScreenVM>()
    val mangaScreenVM = hiltViewModel<MangaScreenVM>()
    val profileScreenVM = hiltViewModel<ProfileScreenVM>()

    //Anime screen
    val trendingAnime = animeScreenVM.trendingAnime.collectAsLazyPagingItems()
    val thisSeasonAnime = animeScreenVM.thisSeasonAnime.collectAsLazyPagingItems()
    val nextSeasonAnime = animeScreenVM.nextSeasonAnime.collectAsLazyPagingItems()
    val allTimePopularAnime = animeScreenVM.allTimePopularAnime.collectAsLazyPagingItems()

    //Manga screen
    val trendingManga = mangaScreenVM.trendingManga.collectAsLazyPagingItems()
    val allTimePopularManga = mangaScreenVM.allTimePopularManga.collectAsLazyPagingItems()
    val popularManhwa = mangaScreenVM.popularManhwa.collectAsLazyPagingItems()

    //Profile screen
    val aniListUser = profileScreenVM.aniListUser.collectAsStateWithLifecycle(
        initialValue = AniListUser()
    ).value
    val chosenContentType = profileScreenVM.chosenContentType.collectAsStateWithLifecycle().value
    val userWatchingAnime = profileScreenVM.userWatchingAnime.collectAsLazyPagingItems()
    val userReWatchingAnime = profileScreenVM.userReWatchingAnime.collectAsLazyPagingItems()
    val userCompletedAnime = profileScreenVM.userCompletedAnime.collectAsLazyPagingItems()
    val userPausedAnime = profileScreenVM.userPausedAnime.collectAsLazyPagingItems()
    val userDroppedAnime = profileScreenVM.userDroppedAnime.collectAsLazyPagingItems()
    val userPlanningAnime = profileScreenVM.userPlanningAnime.collectAsLazyPagingItems()

    val isUserLoggedIn = prefs.getBoolean("loggedIn", false)
    NavHost(
        navController = navController,
        startDestination = if(isUserLoggedIn) {
            NavBarScreensRoute
        } else {
            AuthScreenRoute
        }
    ) {
        navigation<NavBarScreensRoute>(
            startDestination = AnimeScreenRoute
        ) {
            animeScreen(
                navController = navController,
                animeScreenVM = animeScreenVM,
                animeLists = listOf(
                    trendingAnime,
                    thisSeasonAnime,
                    nextSeasonAnime,
                    allTimePopularAnime
                )
            )

            mangaScreen(
                navController = navController,
                mangaScreenVM = mangaScreenVM,
                mangaLists = listOf(
                    trendingManga,
                    allTimePopularManga,
                    popularManhwa
                )
            )

            profileScreen(
                navController = navController,
                profileScreenVM = profileScreenVM,
                aniListUser = aniListUser,
                chosenContentType = chosenContentType,
                userAnimeLists = listOf(
                    userWatchingAnime,
                    userReWatchingAnime,
                    userCompletedAnime,
                    userPausedAnime,
                    userDroppedAnime,
                    userPlanningAnime
                )
            )
        }

        authScreen(
            navController = navController,
            prefs = prefs
        )
    }
}