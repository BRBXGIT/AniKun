package com.example.media_screen.media_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.media_screen.media_screen.screen.MediaDetailsScreen
import com.example.media_screen.media_screen.screen.MediaScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class MediaDetailsScreenRoute(
    val mediaId: Int
)

fun NavGraphBuilder.mediaDetailsScreen() = composable<MediaDetailsScreenRoute> {
    val mediaId = it.toRoute<MediaDetailsScreenRoute>().mediaId
    val mediaScreenVM = hiltViewModel<MediaScreenVM>()

    MediaDetailsScreen(
        mediaId = mediaId,
        viewModel = mediaScreenVM
    )
}