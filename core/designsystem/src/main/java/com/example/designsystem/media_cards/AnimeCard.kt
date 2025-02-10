package com.example.designsystem.media_cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeCard(
    anime: AnimeListMedia,
    index: Int,
    onAnimeClick: () -> Unit,
    onAnimeLongClick: () -> Unit = {},
    listType: String? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(mShapes.small)
            .combinedClickable(
                onClick = { onAnimeClick() },
                onLongClick = { onAnimeLongClick() },
            )
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp, 130.dp)
                .clip(mShapes.small)
                .background(mColors.surfaceVariant)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(anime.coverImage.large)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp, 130.dp)
                    .clip(mShapes.small),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { if (index <= 6) AnimatedShimmer(100.dp, 130.dp) }
            )

            if(listType != null) {
                val listColor = when(listType) {
                    "Completed" -> Color(0xff007c00)
                    "Dropped" -> Color(0xffca3433)
                    "Rewatching" -> Color(0xff67bef6)
                    "Planning" -> Color(0xff0041c1)
                    "Paused" -> Color(0xfff7e788)
                    else -> Color(0xffbb51b9)
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(color = listColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = listType,
                        style = mTypography.labelSmall,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = if (anime.title.english == null) anime.title.romaji else anime.title.english!!,
                style = mTypography.bodyLarge.copy(
                    color = mColors.primary,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${anime.episodes} Episodes • ${
                    anime.averageScore.toString().take(1)
                }.${anime.averageScore.toString().takeLast(1)}★",
                style = mTypography.bodyMedium.copy(
                    color = mColors.secondary,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = AnnotatedString.fromHtml(anime.description.toString()),
                style = mTypography.bodyMedium.copy(
                    color = mColors.tertiary
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }
    }
}