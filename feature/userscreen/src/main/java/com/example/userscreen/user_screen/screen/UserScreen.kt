package com.example.userscreen.user_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.designsystem.container_with_sb.ContainerWithScrollBehavior
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import com.example.userscreen.user_screen.sections.UserFavoritesPager
import com.example.userscreen.user_screen.sections.UserHeader
import com.example.userscreen.user_screen.sections.UserScreenTopBar
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    userName: String,
    viewModel: UserScreenVM,
    navController: NavController,
    profileScreensSharedVM: MediaProfileScreensSharedVM,
    userMangaLists: List<UserMangaLists>?,
    userAnimeLists: List<UserAnimeLists>?,
) {
    //Use launched effect to don't fetch data multiple times
    val userDetails = viewModel.userDetails.collectAsStateWithLifecycle().value
    LaunchedEffect(userDetails) {
        if((userDetails.data == null) and (userDetails.exception == null)) {
            viewModel.fetchUserDetails(userName)
        }
    }

    val containerScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            UserScreenTopBar(
                scrollBehavior = topAppBarScrollBehavior,
                onNavIconClick = { navController.navigateUp() },
                userName = userDetails.data?.user?.name,
                containerScrollBehavior = containerScrollBehavior,
                avatar = userDetails.data?.user?.avatar?.large
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(containerScrollBehavior.nestedScrollConnection)
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if((userDetails.data == null) && (userDetails.exception == null)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if(userDetails.exception != null) {
            ErrorSection(
                errorText = userDetails.exception!!,
                modifier = Modifier.fillMaxSize()
            )
        }

        if(userDetails.data != null) {
            userDetails.data!!.let { userData ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding()),
                ) {
                    ContainerWithScrollBehavior(
                        scrollBehavior = containerScrollBehavior
                    ) {
                        UserHeader(
                            totalAnime = userData.user.statistics.anime.count,
                            minutesWatched = userData.user.statistics.anime.minutesWatched,
                            episodesWatched = userData.user.statistics.anime.episodesWatched,
                            totalManga = userData.user.statistics.manga.count,
                            chaptersRead = userData.user.statistics.manga.chaptersRead,
                            volumesRead = userData.user.statistics.manga.volumesRead,
                        )
                    }

                    UserFavoritesPager(
                        userFavoriteAnime = userData.user.favourites.anime.nodes,
                        userFavoriteManga = userData.user.favourites.manga.nodes,
                        userFavoriteCharacters = userData.user.favourites.characters.nodes,
                        userMangaLists = userMangaLists,
                        userAnimeLists = userAnimeLists,
                        profileScreensSharedVM = profileScreensSharedVM,
                        navController = navController,
                        bottomPadding = innerPadding.calculateBottomPadding()
                    )
                }
            }
        }
    }
}