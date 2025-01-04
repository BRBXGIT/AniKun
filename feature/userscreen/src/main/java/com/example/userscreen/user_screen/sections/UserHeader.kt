package com.example.userscreen.user_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
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
    modifier: Modifier,
    avatar: String,
    totalAnime: Int,
    minutesWatched: Int,
    episodesWatched: Int,
    totalManga: Int,
    chaptersRead: Int,
    volumesRead: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatar)
                .crossfade(500)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(84.dp, 84.dp)
                .clip(mShapes.small),
            filterQuality = FilterQuality.Low,
            contentScale = ContentScale.Crop,
            loading = { AnimatedShimmer(84.dp, 84.dp) }
        )

        val totalWatchingTime = minutesWatched.toDouble() / 1440
        UserInfoBox(
            totalMedia = totalAnime,
            totalWatchingTime = totalWatchingTime,
            totalEpisodes = episodesWatched
        )

        UserInfoBox(
            totalMedia = totalManga,
            totalChapters = chaptersRead,
            volumesRead = volumesRead
        )
    }
}

@Composable
private fun UserInfoBox(
    totalMedia: Int,
    totalWatchingTime: Double? = null,
    totalEpisodes: Int? = null,
    totalChapters: Int? = null,
    volumesRead: Int? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(84.dp)
            .background(
                color = mColors.primaryContainer,
                shape = mShapes.small
            )
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = if(totalChapters != null) "Total Anime: $totalMedia" else "Total Manga: $totalMedia",
                style = mTypography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = if(totalChapters != null) "Chapters Read: $totalChapters" else "Days Watched: ${totalWatchingTime.toString().take(3)}",
                style = mTypography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = if(volumesRead != null) "Volumes Read: $volumesRead" else "Ep. Watched: $totalEpisodes",
                style = mTypography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}