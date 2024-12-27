package com.example.designsystem.media_cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.designsystem.utils.Utils

data class ListType(
    val previewName: String,
    val listType: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaLongClickBS(
    onDismissRequest: () -> Unit,
    title: String,
    episodes: Int,
    averageScore: Int,
    onListClick: (String) -> Unit,
    mediaType: String,
    currentList: String
) {
    val animeListTypes = listOf(
        ListType("Watching", Utils.CURRENT_LIST_TYPE),
        ListType("Planning", Utils.PLANNING_LIST_TYPE),
        ListType("Completed", Utils.COMPLETED_LIST_TYPE),
        ListType("Paused", Utils.PAUSED_LIST_TYPE),
        ListType("Dropped", Utils.DROPPED_LIST_TYPE),
        ListType("Rewatching", Utils.REPEATING_LIST_TYPE)
    )
    val mangaListTypes = listOf(
        ListType("Reading", Utils.CURRENT_LIST_TYPE),
        ListType("Planning", Utils.PLANNING_LIST_TYPE),
        ListType("Completed", Utils.COMPLETED_LIST_TYPE),
        ListType("Paused", Utils.PAUSED_LIST_TYPE),
        ListType("Dropped", Utils.DROPPED_LIST_TYPE),
        ListType("Rereading", Utils.REPEATING_LIST_TYPE)
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = mShapes.small
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = title,
                    style = mTypography.bodyLarge.copy(
                        color = mColors.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$episodes Episodes • ${averageScore.toString().take(1)}.${averageScore.toString().takeLast(1)}★",
                    style = mTypography.bodyMedium.copy(
                        color = mColors.secondary,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            HorizontalDivider(thickness = 1.dp)

            LazyColumn(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if(mediaType == "ANIME") {
                    items(animeListTypes) { type ->
                        ListItem(
                            modifier = Modifier.fillParentMaxWidth(),
                            onListClick = { onListClick(type.listType) },
                            type = type.previewName,
                            currentList = currentList
                        )
                    }
                } else {
                    items(mangaListTypes) { type ->
                        ListItem(
                            modifier = Modifier.fillParentMaxWidth(),
                            onListClick = { onListClick(type.listType) },
                            type = type.previewName,
                            currentList = currentList
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier,
    onListClick: () -> Unit,
    type: String,
    currentList: String
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clip(mShapes.small)
            .clickable { onListClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = type,
            style = mTypography.bodyLarge
        )

        Icon(
            painter = painterResource(
                id = if(currentList == type) AniKunIcons.CheckCircle else AniKunIcons.ArrowRight,
            ),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}