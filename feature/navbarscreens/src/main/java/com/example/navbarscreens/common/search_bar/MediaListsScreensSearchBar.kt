package com.example.navbarscreens.common.search_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.data.remote.models.common_models.media_by_query_response.Media
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mTypography
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaListsScreensSearchBar(
    navController: NavController,
    onSearchClick: (String) -> Unit,
    mediaByQuery: LazyPagingItems<Media>,
    onExpandChange: () -> Unit,
    placeHolderText: String
) {
    var query by rememberSaveable { mutableStateOf("") }

    SearchBar(
        inputField = {
            TextField(
                value = query,
                onValueChange = { query = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                placeholder = {
                    Text(text = placeHolderText)
                },
                leadingIcon = {
                    IconButton(
                        onClick = { onExpandChange() }
                    ) {
                        Icon(
                            painter = painterResource(id = AniKunIcons.ArrowLeftFilled),
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if(query.isNotEmpty()) {
                        IconButton(
                            onClick = { query = "" }
                        ) {
                            Icon(
                                painter = painterResource(id = AniKunIcons.BackspaceFilled),
                                contentDescription = null
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick(query) }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
            )
        },
        expanded = true,
        onExpandedChange = { onExpandChange() },
    ) {
        var errorText by rememberSaveable { mutableStateOf("") }
        LaunchedEffect(mediaByQuery.loadState.refresh) {
            if(mediaByQuery.loadState.refresh is LoadState.Error) {
                errorText = (mediaByQuery.loadState.refresh as LoadState.Error).error.message.toString()
            }
        }

        LazyColumn {
            if(errorText.isBlank()) {
                items(mediaByQuery.itemCount) { index ->
                    val currentMedia = mediaByQuery[index]

                    SearchItem(
                        onItemClick = {
                            navController.navigate(
                                MediaDetailsScreenRoute(
                                    mediaId = currentMedia!!.id,
                                    mediaType = currentMedia.type
                                )
                            )
                            onExpandChange()
                        },
                        media = currentMedia!!,
                        modifier = Modifier.animateItem()
                    )
                }
            } else {
                item {
                    ErrorSection(
                        errorText = errorText,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun SearchItem(
    onItemClick: () -> Unit,
    media: Media,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
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