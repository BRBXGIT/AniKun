package com.example.media_screen.character_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.mTypography

@Composable
fun FavoritesCountSection(
    favoritesCount: Int,
    mediaCount: Int,
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = mediaCount.toString(),
                    style = mTypography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Media",
                    style = mTypography.labelMedium
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = favoritesCount.toString(),
                    style = mTypography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Favorites",
                    style = mTypography.labelMedium
                )
            }
        }
    }
}