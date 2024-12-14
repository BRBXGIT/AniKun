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
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.designsystem.theme.AppSettingsVM
import com.example.media_screen.media_screen.navigation.mediaDetailsScreen
import com.example.navbarscreens.anime_screen.navigation.AnimeScreenRoute
import com.example.navbarscreens.anime_screen.navigation.animeScreen
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM
import com.example.navbarscreens.common.navigation.NavBarScreensRoute
import com.example.navbarscreens.favorites_screen.navigation.favoritesScreen
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.navbarscreens.manga_screen.navigation.mangaScreen
import com.example.navbarscreens.manga_screen.screen.MangaScreenVM
import com.example.navbarscreens.profile_screen.navigation.profileScreen
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.settingsscreen.settings_screen.navigation.settingsScreen

@Composable
fun NavGraph(
    prefs: SharedPreferences,
    appSettingsVM: AppSettingsVM
) {
    val navController = rememberNavController()

    //Initialize values here to don't refresh it after screen will be recomposed
    val animeScreenVM = hiltViewModel<AnimeScreenVM>()
    val mangaScreenVM = hiltViewModel<MangaScreenVM>()
    val mediaProfileScreensSharedVM = hiltViewModel<MediaProfileScreensSharedVM>()
    val mediaFavoritesScreensSharedVM = hiltViewModel<MediaFavoritesScreensSharedVM>()

    //Anime and manga screens
    val trendingAnime = animeScreenVM.trendingAnime.collectAsLazyPagingItems()
    val trendingManga = mangaScreenVM.trendingManga.collectAsLazyPagingItems()

    //Profile screen
    val aniListUser = mediaProfileScreensSharedVM.aniListUser.collectAsStateWithLifecycle(
        initialValue = AniListUser()
    ).value
    val chosenProfileScreenContentType = mediaProfileScreensSharedVM.chosenContentType.collectAsStateWithLifecycle().value
    val isUserLoggedIn = prefs.getBoolean("loggedIn", false)

    val userAnimeLists = mediaProfileScreensSharedVM.userAnimeLists.collectAsStateWithLifecycle(
        initialValue = UserAnimeListsResponse()
    ).value
    val userMangaLists = mediaProfileScreensSharedVM.userMangaLists.collectAsStateWithLifecycle(
        initialValue = UserMangaListsResponse()
    ).value

    //UserFavoritesScreen
    mediaFavoritesScreensSharedVM.fetchUserFavorites(aniListUser.data.viewer.name)
    val userFavorites = mediaFavoritesScreensSharedVM.userFavorites.collectAsStateWithLifecycle().value
    val chosenFavoritesScreenContentType = mediaFavoritesScreensSharedVM.chosenContentType.collectAsStateWithLifecycle().value
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
            animeScreen(
                navController = navController,
                animeScreenVM = animeScreenVM,
                trendingAnime = trendingAnime
            )

            mangaScreen(
                navController = navController,
                mangaScreenVM = mangaScreenVM,
                trendingManga = trendingManga
            )

            favoritesScreen(
                navController = navController,
                userFavorites = userFavorites,
                mediaFavoritesScreensSharedVM = mediaFavoritesScreensSharedVM,
                chosenContentType = chosenFavoritesScreenContentType,
                favoritesException = favoritesException
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

        settingsScreen(
            appSettingsVM = appSettingsVM,
            navController = navController
        )
    }
}