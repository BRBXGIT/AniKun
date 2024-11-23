package com.example.navbarscreens.manga_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.manga_models.manga_list_response.Media as MangaListMedia
import com.example.navbarscreens.manga_screen.screen.MangaScreen
import com.example.navbarscreens.manga_screen.screen.MangaScreenVM
import kotlinx.serialization.Serializable

@Serializable
data object MangaScreenRoute

fun NavGraphBuilder.mangaScreen(
    navController: NavController,
    mangaScreenVM: MangaScreenVM,
    mangaLists: List<LazyPagingItems<MangaListMedia>>
) = composable<MangaScreenRoute> {
    MangaScreen(
        viewModel = mangaScreenVM,
        navController = navController,
        mangaLists = mangaLists
    )
}