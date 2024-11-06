package com.example.navbarscreens.anime_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.anime_screen.sections.AnimeLCSection
import com.example.navbarscreens.topbar.TopBar
import com.example.navbarscreens.navbar.NavBar

@Composable
fun AnimeScreen(
    navController: NavController,
    viewModel: AnimeScreenVM = hiltViewModel<AnimeScreenVM>()
) {
    Scaffold(
        topBar = { TopBar("Find anime") },
        bottomBar = { NavBar(navController) },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val trendingAnime = viewModel.trendingAnime.collectAsLazyPagingItems()
            AnimeLCSection(trendingAnime)
        }
    }
}