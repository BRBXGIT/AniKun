package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.media_details_models.media_details_response.NodeX
import com.example.data.remote.models.media_details_models.media_details_response.Recommendations
import com.example.designsystem.media_cards.AnimatedShimmer
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@Composable
fun RecommendationsLRSection(
    recommendations: Recommendations,
    navController: NavController
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
                RecommendationCard(
                    recommendation = recommendation,
                    onCardClick = {
                        navController.navigate(
                            MediaDetailsScreenRoute(
                                mediaId = recommendation.mediaRecommendation.id,
                                mediaType = recommendation.mediaRecommendation.type
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun RecommendationCard(
    recommendation: NodeX,
    onCardClick: () -> Unit
) {
    Surface(
        onClick = { onCardClick() },
        shape = mShapes.extraSmall,
        color = mColors.surfaceContainer,
        modifier = Modifier
            .height(140.dp)
            .width(300.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recommendation.mediaRecommendation.coverImage.large)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(100.dp, 140.dp),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer(100.dp, 140.dp) }
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(
                        top = 8.dp,
                        end = 8.dp
                    )
                ) {
                    val titleEnglish = recommendation.mediaRecommendation.title.english
                    Text(
                        text = titleEnglish ?: recommendation.mediaRecommendation.title.romaji,
                        style = mTypography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = recommendation.mediaRecommendation.startDate.year.toString(),
                        style = mTypography.labelMedium
                    )

                    val mediaFormat = recommendation.mediaRecommendation.format
                    val mediaEpisodes = if((mediaFormat == "MANGA") or (mediaFormat == "ONE_SHOT")) {
                        recommendation.mediaRecommendation.format
                    } else {
                        "${recommendation.mediaRecommendation.format} • ${recommendation.mediaRecommendation.episodes}"
                    }

                    Text(
                        text = mediaEpisodes,
                        style = mTypography.labelMedium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = AniKunIcons.MedalFilled),
                            contentDescription = null,
                            tint = Color((0xffffbc08))
                        )

                        val score = recommendation.mediaRecommendation.averageScore.toString()
                        Text(
                            text = "${score.take(1)}.${score.takeLast(1)}",
                            style = mTypography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = AniKunIcons.HeartFilled),
                            contentDescription = null,
                            tint = Color((0xffb8110a))
                        )

                        Text(
                            text = recommendation.mediaRecommendation.favourites.toString(),
                            style = mTypography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}