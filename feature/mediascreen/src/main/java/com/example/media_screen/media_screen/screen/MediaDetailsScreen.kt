package com.example.media_screen.media_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.sections.CharactersLRSection
import com.example.media_screen.media_screen.sections.DescriptionSection
import com.example.media_screen.media_screen.sections.GenresLRSection
import com.example.media_screen.media_screen.sections.InfoSection
import com.example.media_screen.media_screen.sections.MediaHeader
import com.example.media_screen.media_screen.sections.MediaScreenTopBar
import com.example.media_screen.media_screen.sections.UserListTypeSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailsScreen(
    mediaId: Int,
    viewModel: MediaScreenVM,
    navController: NavController
) {
    //Get and collect media details
    viewModel.fetchMediaDetailsById(mediaId)
    val mediaDetails = viewModel.mediaDetails.collectAsStateWithLifecycle().value

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            MediaScreenTopBar(
                title = mediaDetails.data?.media?.title,
                scrollBehavior = topBarScrollBehavior,
                onNavIconClick = { navController.navigateUp() },
                onFavoriteIconClick = {  },
                onListIconClick = {  }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if((mediaDetails.data == null) && (mediaDetails.exception != null)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if(mediaDetails.exception != null) {
            ErrorSection(
                errorText = mediaDetails.exception.toString(),
                modifier = Modifier.fillMaxSize()
            )
        }

        if(mediaDetails.data != null) {
            mediaDetails.data!!.media.let { media ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(28.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = innerPadding.calculateBottomPadding())
                ) {
                    item {
                        MediaHeader(
                            topPadding = innerPadding.calculateTopPadding(),
                            coverImage = media.coverImage.large,
                            title = if(media.title.english == null) media.title.romaji else media.title.english!!,
                            year = media.seasonYear,
                            format = media.format,
                            episodes = media.episodes,
                            nextAiringEpisode = media.nextAiringEpisode,
                            bannerImage = media.bannerImage,
                        )
                    }

                    item {
                        UserListTypeSection(
                            score = "${media.averageScore.toString().take(1)}.${media.averageScore.toString().takeLast(1)}",
                            favoritesCount = media.favourites,
                            userListType = "Watching",
                            popularityCount = media.popularity,
                            modifier = Modifier.fillParentMaxWidth()
                        )
                    }

                    item {
                        GenresLRSection(media.genres)
                    }

                    item {
                        DescriptionSection(media.description)
                    }

                    item {
                        CharactersLRSection(media.characters)
                    }

                    item {
                        InfoSection(
                            title = media.title,
                            format = media.format,
                            episodes = media.episodes,
                            episodeDuration = media.duration,
                            source = media.source,
                            status = media.status,
                            startDate = media.startDate,
                            endDate = media.endDate,
                            season = media.season,
                            seasonYear = media.seasonYear
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(0.dp))
                    }
                }
            }
        }
    }
}