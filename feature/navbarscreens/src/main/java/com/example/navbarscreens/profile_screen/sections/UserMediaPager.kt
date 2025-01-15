package com.example.navbarscreens.profile_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_anime_list_response.UserAnimeListsResponse
import com.example.data.remote.models.profile_models.user_manga_list_response.UserMangaListsResponse
import com.example.designsystem.custom_modifiers.customTabIndicatorOffset
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.snackbars.SnackbarAction
import com.example.designsystem.snackbars.SnackbarController
import com.example.designsystem.snackbars.SnackbarEvent
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.media_screen.media_screen.screen.MediaProfileScreensSharedVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserMediaPager(
    userAnime: UserAnimeListsResponse,
    userManga: UserMangaListsResponse,
    chosenContentType: Boolean,
    navController: NavController,
    profileScreensSharedVM: MediaProfileScreensSharedVM
) {
    var selectedType by rememberSaveable { mutableIntStateOf(0) }
    val animationScope = rememberCoroutineScope()

    if(!chosenContentType) {
        if(userAnime.data != null) {
            val pagerState = rememberPagerState(
                pageCount = { userAnime.data!!.mediaListCollection.lists.size }
            )

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    selectedType = page
                }
            }

            ScrollableTabRow(
                selectedTabIndex = selectedType,
                edgePadding = 16.dp,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                    )
                },
                indicator = { tabPositions ->
                    if(selectedType < tabPositions.size) {
                        Box(
                            modifier = Modifier
                                .customTabIndicatorOffset(
                                    currentTabPosition = tabPositions[selectedType],
                                    width = 50.dp
                                )
                                .height(3.dp)
                                .background(
                                    color = mColors.primary,
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                    }
                }
            ) {
                userAnime.data!!.mediaListCollection.lists.forEachIndexed { index, list ->
                    Tab(
                        selected = index == selectedType,
                        onClick = {
                            selectedType = index
                            animationScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(mShapes.small),
                        text = {
                            Text(
                                text = list.name
                            )
                        }
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                UserAnimeLCSection(
                    animeList = userAnime.data!!.mediaListCollection.lists[page].entries,
                    navController = navController,
                    userAnimeLists = userAnime.data!!.mediaListCollection.lists,
                    profileScreensSharedVM = profileScreensSharedVM
                )
            }
        } else {
            var isRefreshing by rememberSaveable { mutableStateOf(false) }
            if((userAnime.exception != null) and (userAnime.exception != "HTTP 404 ")) {
                LaunchedEffect(Unit) {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Something went wrong :(",
                            action = SnackbarAction(
                                name = "Refresh",
                                action = {
                                    isRefreshing = true
                                    profileScreensSharedVM.refreshAniListUser()
                                }
                            )
                        )
                    )
                }
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {  }
            ) {
                if(userAnime.exception != "HTTP 404 ") {
                    ErrorSection(
                        errorText = userAnime.exception.toString(),
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            ErrorSection(
                errorText = userAnime.exception.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    if(chosenContentType) {
        if(userManga.data != null) {
            val pagerState = rememberPagerState(
                pageCount = { userManga.data!!.mediaListCollection.lists.size }
            )

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    selectedType = page
                }
            }

            ScrollableTabRow(
                selectedTabIndex = selectedType,
                edgePadding = 16.dp,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                    )
                },
                indicator = { tabPositions ->
                    if(selectedType < tabPositions.size) {
                        Box(
                            modifier = Modifier
                                .customTabIndicatorOffset(
                                    currentTabPosition = tabPositions[selectedType],
                                    width = 50.dp
                                )
                                .height(3.dp)
                                .background(
                                    color = mColors.primary,
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                    }
                }
            ) {
                userManga.data!!.mediaListCollection.lists.forEachIndexed { index, list ->
                    Tab(
                        selected = index == selectedType,
                        onClick = {
                            selectedType = index
                            animationScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clip(mShapes.small),
                        text = {
                            Text(
                                text = list.name
                            )
                        }
                    )
                }
            }

            HorizontalPager(state = pagerState) { page ->
                UserMangaLVGSection(
                    mangaList = userManga.data!!.mediaListCollection.lists[page].entries,
                    navController = navController,
                    profileScreensSharedVM = profileScreensSharedVM,
                    userMangaLists = userManga.data!!.mediaListCollection.lists
                )
            }
        } else {
            var isRefreshing by rememberSaveable { mutableStateOf(false) }
            if((userManga.exception != null) and (userManga.exception != "HTTP 404 ")) {
                LaunchedEffect(Unit) {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "Something went wrong :(",
                            action = SnackbarAction(
                                name = "Refresh",
                                action = {
                                    isRefreshing = true
                                    profileScreensSharedVM.refreshAniListUser()
                                }
                            )
                        )
                    )
                }
            }

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {  }
            ) {
                if(userManga.exception != "HTTP 404 ") {
                    ErrorSection(
                        errorText = userManga.exception.toString(),
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            ErrorSection(
                errorText = userManga.exception.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}