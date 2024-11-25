package com.example.anilist

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.auth.navigation.AuthScreenRoute
import com.example.auth.navigation.authScreen
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia
import com.example.navbarscreens.anime_screen.navigation.AnimeScreenRoute
import com.example.navbarscreens.anime_screen.navigation.animeScreen
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM
import com.example.navbarscreens.common.navigation.NavBarScreensRoute
import com.example.navbarscreens.manga_screen.navigation.mangaScreen
import com.example.navbarscreens.manga_screen.screen.MangaScreenVM
import com.example.navbarscreens.profile_screen.navigation.ProfileScreenRoute
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

    val animeList = getAnimeLists(animeScreenVM)
    val mangaList = getMangaLists(mangaScreenVM)

    //Profile screen
    val aniListUser = profileScreenVM.aniListUser.collectAsStateWithLifecycle(
        initialValue = AniListUser()
    ).value
    val chosenContentType = profileScreenVM.chosenContentType.collectAsStateWithLifecycle().value
    val isUserLoggedIn = prefs.getBoolean("loggedIn", false)

    val userAnimeList = if(isUserLoggedIn) {
        getUserAnimeList(profileScreenVM)
    } else {
        emptyList()
    }
    val userMangaList = if(isUserLoggedIn) {
        getUserMangaList(profileScreenVM)
    } else {
        emptyList()
    }

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
                animeLists = animeList
            )

            mangaScreen(
                navController = navController,
                mangaScreenVM = mangaScreenVM,
                mangaLists = mangaList
            )

            profileScreen(
                navController = navController,
                profileScreenVM = profileScreenVM,
                aniListUser = aniListUser,
                chosenContentType = chosenContentType,
                userAnimeLists = userAnimeList,
                userMangaLists = userMangaList
            )
        }

        authScreen(
            navController = navController,
            prefs = prefs
        )
    }
}

@Composable
private fun getAnimeLists(animeScreenVM: AnimeScreenVM): List<LazyPagingItems<AnimeListMedia>> {
    val trendingAnime = animeScreenVM.trendingAnime.collectAsLazyPagingItems()
    val thisSeasonAnime = animeScreenVM.thisSeasonAnime.collectAsLazyPagingItems()
    val nextSeasonAnime = animeScreenVM.nextSeasonAnime.collectAsLazyPagingItems()
    val allTimePopularAnime = animeScreenVM.allTimePopularAnime.collectAsLazyPagingItems()

    return listOf(
        trendingAnime,
        thisSeasonAnime,
        nextSeasonAnime,
        allTimePopularAnime
    )
}

@Composable
private fun getMangaLists(mangaScreenVM: MangaScreenVM): List<LazyPagingItems<MangaListMedia>> {
    val trendingManga = mangaScreenVM.trendingManga.collectAsLazyPagingItems()
    val allTimePopularManga = mangaScreenVM.allTimePopularManga.collectAsLazyPagingItems()
    val popularManhwa = mangaScreenVM.popularManhwa.collectAsLazyPagingItems()

    return listOf(
        trendingManga,
        allTimePopularManga,
        popularManhwa
    )
}

@Composable
private fun getUserAnimeList(profileScreenVM: ProfileScreenVM): List<LazyPagingItems<UserAnimeListMedia>> {
    val userWatchingAnime = profileScreenVM.userWatchingAnime.collectAsLazyPagingItems()
    val userReWatchingAnime = profileScreenVM.userReWatchingAnime.collectAsLazyPagingItems()
    val userCompletedAnime = profileScreenVM.userCompletedAnime.collectAsLazyPagingItems()
    val userPausedAnime = profileScreenVM.userPausedAnime.collectAsLazyPagingItems()
    val userDroppedAnime = profileScreenVM.userDroppedAnime.collectAsLazyPagingItems()
    val userPlanningAnime = profileScreenVM.userPlanningAnime.collectAsLazyPagingItems()

    return listOf(
        userWatchingAnime,
        userReWatchingAnime,
        userCompletedAnime,
        userPausedAnime,
        userDroppedAnime,
        userPlanningAnime
    )
}

@Composable
private fun getUserMangaList(profileScreenVM: ProfileScreenVM): List<LazyPagingItems<UserMangaListMedia>> {
    val userReadingManga = profileScreenVM.userReadingManga.collectAsLazyPagingItems()
    val userReReadingManga = profileScreenVM.userReReadingManga.collectAsLazyPagingItems()
    val userCompletedManga = profileScreenVM.userCompletedManga.collectAsLazyPagingItems()
    val userPausedManga = profileScreenVM.userPausedManga.collectAsLazyPagingItems()
    val userDroppedManga = profileScreenVM.userDroppedManga.collectAsLazyPagingItems()
    val userPlanningManga = profileScreenVM.userPlanningManga.collectAsLazyPagingItems()

    return listOf(
        userReadingManga,
        userReReadingManga,
        userCompletedManga,
        userPausedManga,
        userDroppedManga,
        userPlanningManga
    )
}