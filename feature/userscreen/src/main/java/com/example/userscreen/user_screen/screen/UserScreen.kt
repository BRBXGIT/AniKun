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
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.userscreen.user_screen.sections.UserHeader
import com.example.userscreen.user_screen.sections.UserScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(
    userName: String,
    viewModel: UserScreenVM,
    navController: NavController
) {
    //Use launched effect to don't fetch data multiple times
    val userDetails = viewModel.userDetails.collectAsStateWithLifecycle().value
    LaunchedEffect(userDetails) {
        if((userDetails.data == null) and (userDetails.exception == null)) {
            viewModel.fetchUserDetails(userName)
        }
    }

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            UserScreenTopBar(
                scrollBehavior = topBarScrollBehavior,
                onNavIconClick = { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
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
                        .padding(innerPadding)
                ) {
                    UserHeader(
                        userName = userData.user.name,
                        avatar = userData.user.avatar.large,
                        totalAnime = userData.user.statistics.anime.count,
                        minutesWatched = userData.user.statistics.anime.minutesWatched,
                        episodesWatched = userData.user.statistics.anime.episodesWatched,
                        totalManga = userData.user.statistics.manga.count,
                        chaptersRead = userData.user.statistics.manga.chaptersRead,
                        volumesRead = userData.user.statistics.manga.volumesRead
                    )
                }
            }
        }
    }
}