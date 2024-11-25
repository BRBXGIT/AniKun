package com.example.designsystem.manga_card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.designsystem.animated_shimmer.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

@Composable
fun MangaCard(
    manga: MangaListMedia,
    index: Int,
    onCardClick: () -> Unit
) {
    Card(
        shape = mShapes.small,
        onClick = { onCardClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(manga.coverImage.large)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { if(index <= 6) AnimatedShimmer(100.dp, 130.dp) }
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(6.dp)
                    .background(
                        color = mColors.primaryContainer,
                        shape = mShapes.small
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${manga.averageScore.toString().take(1)}.${manga.averageScore.toString().takeLast(1)}",
                    style = mTypography.bodySmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(mColors.secondaryContainer)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if(manga.title.english == null) manga.title.romaji else manga.title.english!!,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = mTypography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )


                Text(
                    text = manga.genres.toString().replace("[", "").replace("]", ""),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = mTypography.bodySmall.copy(
                        color = mColors.secondary
                    )
                )
            }
        }
    }
}