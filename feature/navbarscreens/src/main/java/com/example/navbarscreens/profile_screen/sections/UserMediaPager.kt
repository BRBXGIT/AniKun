package com.example.navbarscreens.profile_screen.sections

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
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.example.designsystem.theme.mColors
import kotlinx.coroutines.launch
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia

@Composable
fun UserMediaPager(
    userAnime: List<LazyPagingItems<UserAnimeListMedia>>? = null,
    userManga: List<LazyPagingItems<UserMangaListMedia>>? = null,
    navController: NavController
) {
    val userAnimeListsType = listOf(
        "Watching",
        "ReWatching",
        "Completed",
        "Paused",
        "Dropped",
        "Planning"
    )
    val userMangaListsType = listOf(
        "Reading",
        "ReReading",
        "Completed",
        "Paused",
        "Dropped",
        "Planning"
    )
    var selectedType by rememberSaveable { mutableIntStateOf(0) }
    val animationScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = { userAnimeListsType.size }
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
        if(userManga != null) {
            userMangaListsType.forEachIndexed { index, type ->
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
        if(userAnime != null) {
            when(page) {
                0 -> UserAnimeLCSection(userAnime[0], navController)
                1 -> UserAnimeLCSection(userAnime[1], navController)
                2 -> UserAnimeLCSection(userAnime[2], navController)
                3 -> UserAnimeLCSection(userAnime[3], navController)
                4 -> UserAnimeLCSection(userAnime[4], navController)
                5 -> UserAnimeLCSection(userAnime[5], navController)
            }
        }
        if(userManga != null) {
            when(page) {
                0 -> UserMangaLVGSection(userManga[0], navController)
                1 -> UserMangaLVGSection(userManga[1], navController)
                2 -> UserMangaLVGSection(userManga[2], navController)
                3 -> UserMangaLVGSection(userManga[3], navController)
                4 -> UserMangaLVGSection(userManga[4], navController)
                5 -> UserMangaLVGSection(userManga[5], navController)
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