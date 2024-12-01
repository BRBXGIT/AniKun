package com.example.media_screen.media_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.data.remote.models.profile_models.user_data_response.AniListUser
import com.example.media_screen.media_screen.screen.MediaDetailsScreen
import com.example.media_screen.media_screen.screen.MediaScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsScreenRoute(
    val mediaId: Int
)

fun NavGraphBuilder.mediaDetailsScreen(
    navController: NavController,
    aniListUser: AniListUser
) = composable<MediaDetailsScreenRoute> {
    val mediaId = it.toRoute<MediaDetailsScreenRoute>().mediaId
    val mediaScreenVM = hiltViewModel<MediaScreenVM>()

    MediaDetailsScreen(
        mediaId = mediaId,
        viewModel = mediaScreenVM,
        navController = navController,
        aniListUser = aniListUser
    )
}