package com.example.media_screen.character_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.character_info_response.Name
import com.example.designsystem.animated_shimmer.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun CharacterHeader(
    image: String,
    name: Name
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
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
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = name.full,
                style = mTypography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = name.native,
                style = mTypography.bodyMedium.copy(
                    color = mColors.primary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}