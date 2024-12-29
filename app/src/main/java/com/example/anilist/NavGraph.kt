package com.example.anilist

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.auth.navigation.AuthScreenRoute
import com.example.auth.navigation.authScreen
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.designsystem.theme.AppSettingsVM
import com.example.media_screen.character_screen.navigation.characterScreen
import com.example.media_screen.media_screen.navigation.mediaDetailsScreen
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.common.navigation.NavBarScreensRoute
import com.example.navbarscreens.favorites_screen.navigation.favoritesScreen
import com.example.navbarscreens.profile_screen.navigation.profileScreen
import com.example.navbarscreens.trending_anime_screen.navigation.AnimeScreenRoute
import com.example.navbarscreens.trending_anime_screen.navigation.trendingAnimeScreen
import com.example.navbarscreens.trending_anime_screen.screen.TrendingAnimeScreenVM
import com.example.navbarscreens.trending_manga_screen.navigation.trendingMangaScreen
import com.example.navbarscreens.trending_manga_screen.screen.TrendingMangaScreenVM
import com.example.settingsscreen.settings_screen.navigation.settingsScreen

@Composable
fun NavGraph(
    prefs: SharedPreferences,
    appSettingsVM: AppSettingsVM
) {
    val navController = rememberNavController()

    //Initialize values here to don't refresh it after screen will be recomposed
    val trendingAnimeScreenVM = hiltViewModel<TrendingAnimeScreenVM>()
    val trendingMangaScreenVM = hiltViewModel<TrendingMangaScreenVM>()
    val mediaProfileScreensSharedVM = hiltViewModel<MediaProfileScreensSharedVM>()
    val mediaFavoritesScreensSharedVM = hiltViewModel<MediaFavoritesScreensSharedVM>()

    //Anime and manga screens
    val trendingAnime = trendingAnimeScreenVM.trendingAnime.collectAsLazyPagingItems()
    val trendingManga = trendingMangaScreenVM.trendingManga.collectAsLazyPagingItems()

    //Profile screen
    val aniListUser = mediaProfileScreensSharedVM.aniListUser.collectAsStateWithLifecycle(
        initialValue = AniListUser()
    ).value
    val chosenProfileScreenContentType = mediaProfileScreensSharedVM.chosenContentType.collectAsStateWithLifecycle().value
    val isUserLoggedIn = prefs.getBoolean("loggedIn", false)

    //Launched to don't fetch data multiple times
    val userAnimeLists = mediaProfileScreensSharedVM.userAnimeLists.collectAsStateWithLifecycle(
        initialValue = UserAnimeListsResponse()
    ).value
    LaunchedEffect(userAnimeLists, aniListUser) {
        if((userAnimeLists.data == null) and (userAnimeLists.exception == null) and (aniListUser.data.viewer.name != "")) {
            mediaProfileScreensSharedVM.fetchUserAnimeLists(aniListUser.data.viewer.name)
        }
    }

    val userMangaLists = mediaProfileScreensSharedVM.userMangaLists.collectAsStateWithLifecycle(
        initialValue = UserMangaListsResponse()
    ).value
    LaunchedEffect(userMangaLists, aniListUser) {
        if((userMangaLists.data == null) and (userMangaLists.exception == null) and (aniListUser.data.viewer.name != "")) {
            mediaProfileScreensSharedVM.fetchUserMangaLists(aniListUser.data.viewer.name)
        }
    }

    //UserFavoritesScreen
    mediaFavoritesScreensSharedVM.fetchUserFavorites(aniListUser.data.viewer.name)
    val userFavorites = mediaFavoritesScreensSharedVM.userFavorites.collectAsStateWithLifecycle().value
    val favoritesException = mediaFavoritesScreensSharedVM.userFavoritesException.collectAsStateWithLifecycle().value

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
            trendingAnimeScreen(
                navController = navController,
                trendingAnimeScreenVM = trendingAnimeScreenVM,
                trendingAnime = trendingAnime,
                userAnimeLists = if(userAnimeLists.data != null) userAnimeLists.data!!.mediaListCollection.lists else null,
                profileScreensSharedVM = mediaProfileScreensSharedVM
            )

            trendingMangaScreen(
                navController = navController,
                trendingMangaScreenVM = trendingMangaScreenVM,
                trendingManga = trendingManga,
                userMangaLists = if(userMangaLists.data != null) userMangaLists.data!!.mediaListCollection.lists else null,
                profileScreensSharedVM = mediaProfileScreensSharedVM
            )

            favoritesScreen(
                navController = navController,
                userFavorites = userFavorites,
                mediaFavoritesScreensSharedVM = mediaFavoritesScreensSharedVM,
                favoritesException = favoritesException,
                userMangaLists = if(userMangaLists.data != null) userMangaLists.data!!.mediaListCollection.lists else null,
                userAnimeLists = if(userAnimeLists.data != null) userAnimeLists.data!!.mediaListCollection.lists else null,
                profileScreensSharedVM = mediaProfileScreensSharedVM
            )

            profileScreen(
                navController = navController,
                mediaProfileScreensSharedVM = mediaProfileScreensSharedVM,
                aniListUser = aniListUser,
                chosenContentType = chosenProfileScreenContentType,
                userAnimeLists = userAnimeLists,
                userMangaLists = userMangaLists
            )
        }

        authScreen(
            navController = navController,
            prefs = prefs
        )

        mediaDetailsScreen(
            navController = navController,
            userMangaLists = if(userMangaLists.data != null) userMangaLists.data!!.mediaListCollection.lists else null,
            userAnimeLists = if(userAnimeLists.data != null) userAnimeLists.data!!.mediaListCollection.lists else null,
            userFavorites = if(favoritesException == null) userFavorites else null,
            favoritesScreenSharedVM = mediaFavoritesScreensSharedVM,
            profileScreensSharedVM = mediaProfileScreensSharedVM
        )

        characterScreen(
            navController = navController,
            favoritesScreenSharedVM = mediaFavoritesScreensSharedVM,
            userFavorites = if(favoritesException == null) userFavorites else null
        )

        settingsScreen(
            appSettingsVM = appSettingsVM,
            navController = navController
        )
    }
}