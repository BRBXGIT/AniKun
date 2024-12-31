package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.media_screen.media_screen.screen.MediaScreenVM

@Composable
fun GenresLRSection(
    genres: List<String>,
    viewModel: MediaScreenVM,
    mediaType: String,
    navController: NavController,
    topPadding: Dp
) {
    var mediaByGenreBSOpen by rememberSaveable { mutableStateOf(false) }
    if(mediaByGenreBSOpen) {
        MediaListByGenreBS(
            viewModel = viewModel,
            mediaType = mediaType,
            navController = navController,
            onDismissRequest = { mediaByGenreBSOpen = false },
            topPadding = topPadding,
        )
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            GenreBox(
                genre = genre,
                onClick = {
                    viewModel.setGenre(genre)
                    mediaByGenreBSOpen = true
                }
            )
        }
    }
}

@Composable
private fun GenreBox(
    genre: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(mShapes.extraSmall)
            .background(mColors.secondaryContainer)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre,
            style = mTypography.labelMedium,
            modifier = Modifier.padding(4.dp)
        )
    }
}