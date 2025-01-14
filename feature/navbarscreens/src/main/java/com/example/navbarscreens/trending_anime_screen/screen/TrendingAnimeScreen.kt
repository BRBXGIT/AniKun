package com.example.navbarscreens.trending_anime_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.snackbars.ObserveAsEvents
import com.example.designsystem.snackbars.SnackbarController
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.common.bars.MediaListsScreensSearchBar
import com.example.navbarscreens.common.bars.NavBar
import com.example.navbarscreens.common.bars.NavBarScreensTopBar
import com.example.navbarscreens.trending_anime_screen.sections.AnimeLCSection
import com.example.settingsscreen.settings_screen.navigation.SettingsScreenRoute
import kotlinx.coroutines.launch
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingAnimeScreen(
    navController: NavController,
    viewModel: TrendingAnimeScreenVM,
    trendingAnime: LazyPagingItems<AnimeListMedia>,
    userAnimeLists: List<UserAnimeLists>?,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
    var isSearching by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            NavBarScreensTopBar(
                text = "Trending anime",
                scrollBehavior = topBarScrollBehaviour,
                onSearchClick = { isSearching = true },
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
            val animeByQuery = viewModel.animeByQuery.collectAsLazyPagingItems()

            MediaListsScreensSearchBar(
                navController = navController,
                placeHolderText = "Find anime",
                onExpandChange = { isSearching = false },
                onSearchClick = { viewModel.setQuery(it) },
                mediaByQuery = animeByQuery
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimeLCSection(
                anime = trendingAnime,
                navController = navController,
                userAnimeLists = userAnimeLists,
                profileScreensSharedVM = profileScreensSharedVM
            )
        }
    }
}