package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.border
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
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun UserListTypeSection(
    score: String,
    favoritesCount: Int,
    userListType: String,
    popularityCount: Int,
    modifier: Modifier,
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
                    text = score,
                    style = mTypography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Score",
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = popularityCount.toString(),
                    style = mTypography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "Popularity",
                    style = mTypography.labelMedium
                )
            }

            Box(
                modifier = Modifier.border(
                    width = 1.dp,
                    shape = mShapes.small,
                    color = mColors.secondary
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userListType,
                    style = mTypography.labelMedium.copy(
                        color = mColors.secondary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    )
                )
            }
        }
    }
}