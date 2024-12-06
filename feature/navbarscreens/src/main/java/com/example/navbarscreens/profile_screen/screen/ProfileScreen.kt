package com.example.navbarscreens.profile_screen.screen

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
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.common.navbar.NavBar
import com.example.navbarscreens.profile_screen.sections.UserMediaPager
import com.example.navbarscreens.common.search_bar.NavbarScreensSearchBar
import com.example.navbarscreens.common.topbar.NavBarScreensTopBar
import com.example.settingsscreen.settings_screen.navigation.SettingsScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenVM,
    aniListUser: AniListUser,
    chosenContentType: Boolean,
    userAnimeLists: List<LazyPagingItems<UserAnimeListMedia>>,
    userMangaLists: List<LazyPagingItems<UserMangaListMedia>>
) {
    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var isSearching by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            NavBarScreensTopBar(
                userName = aniListUser.data.viewer.name,
                userAvatar = aniListUser.data.viewer.avatar.large,
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
            val userByQuery = viewModel.userByQuery.collectAsStateWithLifecycle().value

            NavbarScreensSearchBar(
                placeHolderText = "Find user",
                onExpandChange = { isSearching = false },
                onSearchClick = { viewModel.setQuery(it) },
                userByQuery = userByQuery
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if(!chosenContentType) {
                UserMediaPager(
                    userAnime = userAnimeLists,
                    navController = navController
                )
            } else {
                UserMediaPager(
                    userManga = userMangaLists,
                    navController = navController
                )
            }
        }
    }
}