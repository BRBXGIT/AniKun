package com.example.navbarscreens.common.search_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.remote.models.common_models.media_by_query_response.Media as MediaByQueryMedia
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mTypography

@Composable
fun SearchItem(
    onExpandChange: () -> Unit,
    media: MediaByQueryMedia,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onExpandChange() }
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = AniKunIcons.Magnifier),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = if(media.title.english == null) media.title.romaji else media.title.english!!,
                style = mTypography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            val genres = media.genres.take(3).toString().replace("[", "").replace("]", "")
            val score = "${media.averageScore.toString().take(1)}.${media.averageScore.toString().takeLast(1)}"
            Text(
                text = "$score • ${if(genres == "") "Unspecified" else genres}",
                style = mTypography.labelMedium.copy(
                    color = mColors.secondary
                )
            )
        }
    }
}