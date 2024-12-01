package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.media_details_response.Recommendations
import com.example.designsystem.animated_shimmer.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun RecommendationsLRSection(
    recommendations: Recommendations
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Recommendations",
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(recommendations.nodes) { recommendation ->
                Surface(
                    onClick = {  },
                    shape = mShapes.extraSmall,
                    color = mColors.surfaceContainer,
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(recommendation.coverImage.large)
                                .crossfade(500)
                                .size(Size.ORIGINAL)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp, 140.dp),
                            filterQuality = FilterQuality.Low,
                            contentScale = ContentScale.Crop,
                            loading = { AnimatedShimmer(100.dp, 140.dp) }
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(
                                top = 8.dp,
                                end = 8.dp
                            )
                        ) {
                            Text(
                                text = if(recommendation.title.english == null) recommendation.title.romaji else recommendation.title.english!!,
                                style = mTypography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                text = recommendation.seasonYear.toString(),
                                style = mTypography.labelMedium
                            )

                            Text(
                                text = "${recommendation.format} • ${recommendation.episodes} episodes",
                                style = mTypography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}