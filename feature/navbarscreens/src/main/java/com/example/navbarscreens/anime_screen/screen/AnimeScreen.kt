package com.example.navbarscreens.anime_screen.screen

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.anime_screen.sections.AnimeLCSection
import com.example.designsystem.anime_manga_pager.AnimeMangaListsPager
import com.example.navbarscreens.topbar.TopBar
import com.example.navbarscreens.navbar.NavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen(
    navController: NavController,
    viewModel: AnimeScreenVM = hiltViewModel<AnimeScreenVM>()
) {
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = { TopBar("Find anime", topBarScrollBehaviour) },
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
            val trendingAnime = viewModel.trendingAnime.collectAsLazyPagingItems()
            val allTimePopularAnime = viewModel.allTimePopularAnime.collectAsLazyPagingItems()

            AnimeMangaListsPager {
                AnimeLCSection(trendingAnime)
            }

            AnimeLCSection(trendingAnime)
        }
    }
}