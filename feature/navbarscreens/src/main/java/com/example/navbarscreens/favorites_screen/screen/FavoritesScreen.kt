package com.example.navbarscreens.favorites_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.navbarscreens.common.navbar.NavBar
import com.example.navbarscreens.common.topbar.NavBarScreensTopBar
import com.example.navbarscreens.favorites_screen.sections.FavoriteAnimeLCSection
import com.example.navbarscreens.favorites_screen.sections.FavoriteMangaLVGSection
import com.example.settingsscreen.settings_screen.navigation.SettingsScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    userFavorites: Favourites,
    favoritesException: String?,
    viewModel: MediaFavoritesScreensSharedVM,
    chosenContentType: Boolean
) {
    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            NavBarScreensTopBar(
                text = "Favorites",
                scrollBehavior = topBarScrollBehaviour,
                onSearchClick = {  },
                chosenContent = chosenContentType,
                onContentClick = { viewModel.setContentType(it) },
                onSettingsClick = { navController.navigate(SettingsScreenRoute) }
            )
        },
        bottomBar = { NavBar(navController) },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehaviour.nestedScrollConnection)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(favoritesException == null) {
                if(!chosenContentType) {
                    FavoriteAnimeLCSection(
                        favoriteAnime = userFavorites.anime.nodes,
                        navController = navController
                    )
                } else {
                    FavoriteMangaLVGSection(
                        favoriteManga = userFavorites.manga.nodes,
                        navController = navController
                    )
                }
            } else {
                ErrorSection(
                    errorText = favoritesException.toString(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}