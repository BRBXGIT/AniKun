package com.example.navbarscreens.common.pager

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.manga_models.manga_list_response.Media as MangaListMedia
import com.example.data.remote.models.anime_models.anime_list_response.Media as AnimeListMedia
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.anime_screen.sections.AnimeLCSection
import com.example.navbarscreens.manga_screen.sections.MangaLVGSection
import com.example.navbarscreens.profile_screen.sections.UserAnimeLCSection
import kotlinx.coroutines.launch

@Composable
fun CommonPager(
    anime: List<LazyPagingItems<AnimeListMedia>>? = null,
    manga: List<LazyPagingItems<MangaListMedia>>? = null,
    userAnime: List<LazyPagingItems<UserAnimeListMedia>>? = null
) {
    val animeListsType = listOf(
        "Trending",
        "This season",
        "Next season",
        "All time popular"
    )
    val mangaListsType = listOf(
        "Trending",
        "All time popular",
        "Popular manhwa"
    )
    val userAnimeListsType = listOf(
        "Watching",
        "ReWatching",
        "Completed",
        "Paused",
        "Dropped",
        "Planning"
    )
    var selectedType by rememberSaveable { mutableIntStateOf(0) }
    val animationScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            if(anime != null) {
                animeListsType.size
            } else if(manga != null) {
                mangaListsType.size
            } else {
                userAnimeListsType.size
            }
        }
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
        if(anime != null) {
            animeListsType.forEachIndexed { index, type ->
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
                            text = type
                        )
                    }
                )
            }
        }
        if(manga != null) {
            mangaListsType.forEachIndexed { index, type ->
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
                            text = type
                        )
                    }
                )
            }
        }
        if(userAnime != null) {
            userAnimeListsType.forEachIndexed { index, type ->
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
                            text = type
                        )
                    }
                )
            }
        }
    }

    HorizontalPager(state = pagerState) { page ->
        if(anime != null) {
            when(page) {
                0 -> AnimeLCSection(anime[0])
                1 -> AnimeLCSection(anime[1])
                2 -> AnimeLCSection(anime[2])
                3 -> AnimeLCSection(anime[3])
            }
        }
        if(manga != null) {
            when(page) {
                0 -> MangaLVGSection(manga[0])
                1 -> MangaLVGSection(manga[1])
                2 -> MangaLVGSection(manga[2])
            }
        }
        if(userAnime != null) {
            when(page) {
                0 -> UserAnimeLCSection(userAnime[0])
                1 -> UserAnimeLCSection(userAnime[1])
                2 -> UserAnimeLCSection(userAnime[2])
                3 -> UserAnimeLCSection(userAnime[3])
                4 -> UserAnimeLCSection(userAnime[4])
                5 -> UserAnimeLCSection(userAnime[5])
            }
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
private fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    width: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .width(width)
        .offset(x = max(0.dp, currentTabWidth - width) / 2 + indicatorOffset)
}