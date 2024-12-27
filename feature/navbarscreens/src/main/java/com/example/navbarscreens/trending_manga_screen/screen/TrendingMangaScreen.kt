package com.example.navbarscreens.trending_manga_screen.screen

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
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.common.bars.NavBar
import com.example.navbarscreens.common.bars.MediaListsScreensSearchBar
import com.example.navbarscreens.common.bars.NavBarScreensTopBar
import com.example.navbarscreens.trending_manga_screen.sections.MangaLVGSection
import com.example.settingsscreen.settings_screen.navigation.SettingsScreenRoute
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaScreen(
    navController: NavController,
    viewModel: TrendingMangaScreenVM,
    trendingManga: LazyPagingItems<MangaListMedia>
) {
    val topBarScrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()

    var isSearching by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            NavBarScreensTopBar(
                text = "Trending manga",
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
            val mangaByQuery = viewModel.mangaByQuery.collectAsLazyPagingItems()

            MediaListsScreensSearchBar(
                placeHolderText = "Find manga",
                onExpandChange = { isSearching = false },
                onSearchClick = { viewModel.setQuery(it) },
                mediaByQuery = mangaByQuery,
                navController = navController
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MangaLVGSection(
                manga = trendingManga,
                navController = navController
            )
        }
    }
}