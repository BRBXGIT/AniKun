package com.example.media_screen.media_screen.sections

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.media_details_models.media_details_response.ExternalLink
import com.example.designsystem.media_cards.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun LinksSection(
    links: List<ExternalLink>
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Links",
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                horizontal = 16.dp
            )
        ) {
            items(links) { link ->
                LinkPreview(
                    link = link,
                    onClick = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(link.url)
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun LinkPreview(
    link: ExternalLink,
    onClick: () -> Unit
) {
    val color = if(link.color != null) {
        parseHexColor(link.color!!)
    } else {
        mColors.primaryContainer
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .clip(mShapes.extraSmall)
            .background(color)
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(link.icon)
                .crossfade(500)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clip(mShapes.small),
            filterQuality = FilterQuality.Low,
            contentScale = ContentScale.Crop,
            loading = { AnimatedShimmer(20.dp, 20.dp) }
        )

        Text(
            text = link.site,
            style = mTypography.labelLarge
        )
    }
}

fun parseHexColor(hex: String): Color {
    val colorInt = hex.removePrefix("#").toLong(16).toInt()
    return Color(colorInt or 0xFF000000.toInt())
}