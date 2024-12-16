package com.example.navbarscreens.favorites_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM
import com.example.navbarscreens.common.navbar.NavBar
import com.example.navbarscreens.common.topbar.NavBarScreensTopBar
import com.example.navbarscreens.favorites_screen.sections.FavoriteAnimeLCSection
import com.example.navbarscreens.favorites_screen.sections.FavoriteMangaLVGSection
import com.example.navbarscreens.favorites_screen.sections.FavoritesScreenSearchBar
import com.example.settingsscreen.settings_screen.navigation.SettingsScreenRoute
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

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

    var isSearching by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            NavBarScreensTopBar(
                text = "Favorites",
                scrollBehavior = topBarScrollBehaviour,
                onSearchClick = { isSearching = true },
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
        if(isSearching) {
            val query = viewModel.userFavoritesQuery.collectAsStateWithLifecycle().value

            val filteredAnime = filterAnimeList(query, userFavorites.anime.nodes)
            val filteredManga = filterMangaList(query, userFavorites.manga.nodes)

            FavoritesScreenSearchBar(
                placeHolderText = if(!chosenContentType) "Find favorite anime" else "Find favorite manga",
                onExpandChange = { isSearching = false },
                onSearchClick = { viewModel.setQuery(it) },
                navController = navController,
                chosenContentType = chosenContentType,
                favoriteAnimeByQuery = filteredAnime,
                favoriteMangaByQuery = filteredManga,
            )
        }

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

//My realization for non strong filtration
private fun filterAnimeList(query: String, items: List<AnimeListMedia>): List<AnimeListMedia> {
    return items.filter { item ->
        if(item.title.english != null) {
            item.title.english!!.contains(query, ignoreCase = true) || levenshteinDistance(item.title.english!!.lowercase(), query.lowercase()) <= 2
        } else {
            item.title.romaji.contains(query, ignoreCase = true) || levenshteinDistance(item.title.romaji.lowercase(), query.lowercase()) <= 2
        }
    }
}

private fun filterMangaList(query: String, items: List<MangaListMedia>): List<MangaListMedia> {
    return items.filter { item ->
        if(item.title.english != null) {
            item.title.english!!.contains(query, ignoreCase = true) || levenshteinDistance(item.title.english!!.lowercase(), query.lowercase()) <= 2
        } else {
            item.title.romaji.contains(query, ignoreCase = true) || levenshteinDistance(item.title.romaji.lowercase(), query.lowercase()) <= 2
        }
    }
}

private fun levenshteinDistance(lhs: String, rhs: String): Int {
    val lhsLength = lhs.length
    val rhsLength = rhs.length

    val dp = Array(lhsLength + 1) { IntArray(rhsLength + 1) }

    for (i in 0..lhsLength) {
        for (j in 0..rhsLength) {
            dp[i][j] = when {
                i == 0 -> j
                j == 0 -> i
                else -> minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + if (lhs[i - 1] == rhs[j - 1]) 0 else 1
                )
            }
        }
    }
    return dp[lhsLength][rhsLength]
}