package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.media_details_response.NextAiringEpisode
import com.example.designsystem.animated_shimmer.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun MediaHeader(
    coverImage: String,
    title: String,
    year: Int,
    format: String,
    episodes: Int,
    nextAiringEpisode: NextAiringEpisode?,
    bannerImage: String?,
    topPadding: Dp,
) {
    val hazeState = remember { HazeState() }

    Box {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(bannerImage)
                .crossfade(500)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .haze(hazeState),
            filterQuality = FilterQuality.Low,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .hazeChild(
                    state = hazeState,
                    style = HazeStyle(
                        backgroundColor = mColors.background,
                        blurRadius = 8.dp,
                        tint = HazeTint(
                            color = mColors.background.copy(alpha = 0.5f),
                            blendMode = BlendMode.SrcOver
                        )
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                mColors.background
                            )
                        )
                    )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = topPadding,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )

        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(coverImage)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp, 140.dp)
                    .clip(mShapes.small),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer(100.dp, 140.dp) }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    style = mTypography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = year.toString(),
                    style = mTypography.bodyMedium
                )

                Text(
                    text = "$format • $episodes episodes",
                    style = mTypography.bodyMedium
                )

                if(nextAiringEpisode != null) {
                    val formattedDate = formatMillisecondsToDate(nextAiringEpisode.airingAt.toLong())
                    Text(
                        text = "Ep. ${nextAiringEpisode.episode} at $formattedDate",
                        style = mTypography.bodyMedium.copy(
                            color = mColors.primary
                        )
                    )
                }
            }
        }
    }
}

fun formatMillisecondsToDate(airingAt: Long): String {
    val zoneId = ZoneId.systemDefault()

    val dateTime = LocalDateTime.ofEpochSecond(
        airingAt, 0, zoneId.rules.getOffset(Instant.ofEpochSecond(airingAt))
    )

    val formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm")
    return dateTime.format(formatter)
}