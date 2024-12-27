package com.example.media_screen.character_screen.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.character_info_response.Node
import com.example.designsystem.media_cards.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@Composable
fun SeriesLRSection(
    series: List<Node>,
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Series",
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                horizontal = 16.dp
            )
        ) {
            items(series) { item ->
                SeriesItem(
                    image = item.coverImage.large,
                    type = item.type,
                    title = if(item.title.english != null) item.title.english!! else item.title.romaji,
                    onItemClick = {
                        navController.navigate(
                            MediaDetailsScreenRoute(
                                mediaId = item.id,
                                mediaType = item.type
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun SeriesItem(
    image: String,
    type: String,
    title: String,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(mShapes.small)
            .width(100.dp)
            .clickable { onItemClick() }
    ) {
        Box(
            modifier = Modifier.size(100.dp, 130.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(500)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                filterQuality = FilterQuality.Low,
                contentScale = ContentScale.Crop,
                loading = { AnimatedShimmer(100.dp, 130.dp) }
            )

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .background(
                        color = mColors.background.copy(alpha = 0.8f),
                        shape = mShapes.extraSmall
                    )
                    .padding(4.dp)
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = type,
                    style = mTypography.labelMedium
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(mColors.tertiaryContainer)
                .padding(4.dp)
        ) {
            Text(
                text = title,
                style = mTypography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}