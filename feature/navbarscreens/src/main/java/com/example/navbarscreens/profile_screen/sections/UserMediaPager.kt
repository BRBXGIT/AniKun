package com.example.navbarscreens.profile_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.designsystem.theme.mColors
import kotlinx.coroutines.launch

@Composable
fun UserMediaPager(
    userAnime: UserAnimeListsResponse,
    userManga: UserMangaListsResponse,
    chosenContentType: Boolean,
    navController: NavController
) {
    var selectedType by rememberSaveable { mutableIntStateOf(0) }
    val animationScope = rememberCoroutineScope()

    if(!chosenContentType) {
        if(userAnime.exception == null) {
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
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
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
                    navController = navController
                )
            }
        } else {
            ErrorSection(
                errorText = userAnime.exception.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    if(chosenContentType) {
        if(userManga.exception == null) {
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
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
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
                    navController = navController
                )
            }
        } else {
            ErrorSection(
                errorText = userManga.exception.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}