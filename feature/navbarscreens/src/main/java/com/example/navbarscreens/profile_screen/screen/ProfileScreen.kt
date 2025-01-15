package com.example.navbarscreens.profile_screen.screen

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.designsystem.snackbars.ObserveAsEvents
import com.example.designsystem.snackbars.SnackbarController
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.navbarscreens.common.bars.NavBar
import com.example.navbarscreens.common.bars.NavBarScreensTopBar
import com.example.navbarscreens.profile_screen.sections.ProfileScreenSearchBar
import com.example.navbarscreens.profile_screen.sections.UserMediaPager
import com.example.settingsscreen.settings_screen.navigation.SettingsScreenRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: MediaProfileScreensSharedVM,
    aniListUser: AniListUser,
    chosenContentType: Boolean,
    userAnimeLists: UserAnimeListsResponse,
    userMangaLists: UserMangaListsResponse,
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

    val topBarScrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSearching by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            NavBarScreensTopBar(
                userName = aniListUser.data.viewer.name,
                userAvatar = aniListUser.data.viewer.avatar.large,
                scrollBehavior = topBarScrollBehaviour,
                onSearchClick = { isSearching = true },
                chosenContent = chosenContentType,
                onContentClick = { viewModel.setContentType(it) },
                onSettingsClick = { navController.navigate(SettingsScreenRoute) },
                fromUserListsScreen = true
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

            ProfileScreenSearchBar(
                placeHolderText = "Find user",
                onExpandChange = { isSearching = false },
                onSearchClick = { viewModel.setQuery(it) },
                userByQuery = userByQuery,
                navController = navController
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            UserMediaPager(
                chosenContentType = chosenContentType,
                userAnime = userAnimeLists,
                userManga = userMangaLists,
                navController = navController,
                profileScreensSharedVM = viewModel
            )
        }
    }
}