package com.example.userscreen.user_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.designsystem.media_cards.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun UserHeader(
    userName: String,
    avatar: String,
    totalAnime: Int,
    minutesWatched: Int,
    episodesWatched: Int,
    totalManga: Int,
    chaptersRead: Int,
    volumesRead: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatar)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp, 100.dp)
                    .clip(CircleShape),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer(100.dp, 100.dp) }
            )

            Text(
                text = userName,
                style = mTypography.titleMedium
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val totalWatchingTime = minutesWatched.toDouble() / 1440

            UserInfoRow(
                totalMedia = totalAnime,
                totalTime = totalWatchingTime,
                totalEpisodes = episodesWatched
            )

            UserInfoRow(
                totalMedia = totalManga,
                totalChapters = chaptersRead,
                volumesRead = volumesRead
            )
        }
    }
}

@Composable
private fun UserInfoRow(
    totalMedia: Int,
    totalTime: Double? = null,
    totalEpisodes: Int? = null,
    totalChapters: Int? = null,
    volumesRead: Int? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = mColors.primaryContainer,
                shape = mShapes.small
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(0.33f)
        ) {
            Text(
                text = totalMedia.toString(),
                style = mTypography.bodyMedium
            )

            Text(
                text = if(totalChapters != null) "Total Anime" else "Total Manga",
                style = mTypography.labelMedium
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(0.33f)
        ) {
            Text(
                text = totalChapters?.toString() ?: totalTime.toString().take(3),
                style = mTypography.bodyMedium
            )

            Text(
                text = if(totalChapters != null) "Chapters Read" else "Days Watched",
                style = mTypography.labelMedium
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(0.33f)
        ) {
            Text(
                text = volumesRead?.toString() ?: totalEpisodes.toString(),
                style = mTypography.bodyMedium
            )

            Text(
                text = if(volumesRead != null) "Volumes Read" else "Ep. Watched",
                style = mTypography.labelMedium
            )
        }
    }
}