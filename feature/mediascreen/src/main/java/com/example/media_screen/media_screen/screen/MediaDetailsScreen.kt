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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.common.check_functions.checkIsMediaInFavorites
import com.example.common.check_functions.checkIsMediaInUserList
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.media_screen.media_screen.sections.AddToListBS
import com.example.media_screen.media_screen.sections.CharactersLRSection
import com.example.media_screen.media_screen.sections.DescriptionSection
import com.example.media_screen.media_screen.sections.GenresLRSection
import com.example.media_screen.media_screen.sections.InfoSection
import com.example.media_screen.media_screen.sections.LinksSection
import com.example.media_screen.media_screen.sections.MediaHeader
import com.example.media_screen.media_screen.sections.MediaScreenTopBar
import com.example.media_screen.media_screen.sections.RecommendationsLRSection
import com.example.media_screen.media_screen.sections.TagsSection
import com.example.media_screen.media_screen.sections.UserListTypeSection
import com.example.data.remote.models.profile_models.user_anime_list_response.Lists as UserAnimeLists
import com.example.data.remote.models.profile_models.user_manga_list_response.Lists as UserMangaLists

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaDetailsScreen(
    mediaType: String,
    mediaId: Int,
    viewModel: MediaScreenVM,
    navController: NavController,
    userMangaLists: List<UserMangaLists>?,
    userAnimeLists: List<UserAnimeLists>?,
    userFavorites: Favourites?,
    favoritesScreenSharedVM: MediaFavoritesScreensSharedVM,
    profileScreenSharedVM: MediaProfileScreensSharedVM
) {
    //Get and collect media details
    val mediaDetails = viewModel.mediaDetails.collectAsStateWithLifecycle().value
    //Use LaunchedEffect to don't fetch data multiple times
    LaunchedEffect(mediaDetails) {
        if((mediaDetails.data == null) and (mediaDetails.exception == null)) {
            viewModel.fetchMediaDetailsById(mediaId)
        }
    }
    val type = viewModel.mediaType.collectAsStateWithLifecycle().value
    LaunchedEffect(type) {
        if(type == "") {
            viewModel.setMediaType(mediaType)
        }
    }
    var userListType by rememberSaveable { mutableStateOf("") }
    userListType = if((userAnimeLists != null) and (userMangaLists != null)) {
        checkIsMediaInUserList(userMangaLists!!, userAnimeLists!!, mediaId)
    } else {
        "Error :("
    }
    var isMediaInFavorites by rememberSaveable { mutableStateOf(false) }
    isMediaInFavorites = if(userFavorites != null) {
        checkIsMediaInFavorites(userFavorites, mediaId)
    } else {
        false
    }

    var addToListBSOpen by rememberSaveable { mutableStateOf(false) }
    if(addToListBSOpen) {
        AddToListBS(
            onDismissRequest = { addToListBSOpen = false },
            mediaType = mediaType,
            onListClick = { listType ->
                addToListBSOpen = false
                profileScreenSharedVM.changeMediaListType(mediaId, listType, mediaType)
            },
            currentList = userListType
        )
    }

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            MediaScreenTopBar(
                title = mediaDetails.data?.media?.title,
                scrollBehavior = topBarScrollBehavior,
                onNavIconClick = { navController.navigateUp() },
                onFavoriteIconClick = {
                    favoritesScreenSharedVM.toggleFavorite(mediaType, mediaId)
                    isMediaInFavorites = !isMediaInFavorites
                },
                onListIconClick = { addToListBSOpen = true },
                isFavorite = isMediaInFavorites,
                isInList = userListType != "Not in list"
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if((mediaDetails.data == null) && (mediaDetails.exception == null)) {
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
                errorText = mediaDetails.exception!!,
                modifier = Modifier.fillMaxSize()
            )
        }

        if(mediaDetails.data != null) {
            mediaDetails.data!!.media.let { media ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier.fillMaxSize()
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
                            userListType = userListType,
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
                        CharactersLRSection(
                            characters = media.characters,
                            navController = navController
                        )
                    }

                    item {
                        InfoSection(
                            title = media.title,
                            format = media.format,
                            episodes = media.episodes,
                            chapters = media.chapters,
                            episodeDuration = media.duration,
                            source = media.source,
                            status = media.status,
                            startDate = media.startDate,
                            endDate = media.endDate,
                            season = media.season,
                            seasonYear = media.seasonYear,
                            studios = media.studios
                        )
                    }

                    item {
                        TagsSection(media.tags)
                    }

                    item {
                        RecommendationsLRSection(
                            recommendations = media.recommendations,
                            navController = navController
                        )
                    }

                    item {
                        LinksSection(media.externalLinks)
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .height(0.dp)
                                .padding(
                                    bottom = innerPadding.calculateBottomPadding()
                                )
                        )
                    }
                }
            }
        }
    }
}