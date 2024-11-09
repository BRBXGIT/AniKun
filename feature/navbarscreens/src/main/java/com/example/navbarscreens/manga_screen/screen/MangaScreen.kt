package com.example.navbarscreens.manga_screen.screen

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
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.common.navbar.NavBar
import com.example.navbarscreens.common.pager.CommonPager
import com.example.navbarscreens.common.topbar.NavBarScreensTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaScreen(
    navController: NavController,
    viewModel: MangaScreenVM
) {
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            NavBarScreensTopBar(
                text = "Manga",
                scrollBehavior = topBarScrollBehaviour,
                onSearchClick = {  }
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
            CommonPager(
                anime = emptyList()
            )
        }
    }
}