package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToListBS(
    onDismissRequest: () -> Unit,
    mediaType: String,
    onListClick: () -> Unit,
    currentList: String
) {
    val animeListTypes = listOf(
        "Watching",
        "Planning",
        "Completed",
        "Paused",
        "Dropped",
        "Rewatching"
    )
    val mangaListTypes = listOf(
        "Reading",
        "Planning",
        "Completed",
        "Paused",
        "Dropped",
        "Rereading"
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = mShapes.small
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 16.dp
            ),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if(mediaType == "ANIME") {
                items(animeListTypes) { type ->
                    ListItem(
                        modifier = Modifier.fillParentMaxWidth(),
                        onListClick = { onListClick() },
                        type = type,
                        currentList = currentList
                    )
                }
            } else {
                items(mangaListTypes) { type ->
                    ListItem(
                        modifier = Modifier.fillParentMaxWidth(),
                        onListClick = { onListClick() },
                        type = type,
                        currentList = currentList
                    )
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