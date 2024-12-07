package com.example.navbarscreens.common.search_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.data.remote.models.profile_models.user_by_query_response.UserByQueryResponse
import com.example.designsystem.animated_shimmer.AnimatedShimmer
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mTypography
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.data.remote.models.common_models.media_by_query_response.Media as MediaByQueryMedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavbarScreensSearchBar(
    navController: NavController,
    onSearchClick: (String) -> Unit,
    mediaByQuery: LazyPagingItems<MediaByQueryMedia>? = null,
    onExpandChange: () -> Unit,
    placeHolderText: String,
    userByQuery: UserByQueryResponse? = null
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
        if(mediaByQuery != null) {
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
        } else {
            if(userByQuery!!.exception == null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {  }
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = AniKunIcons.Magnifier),
                        contentDescription = null
                    )

                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userByQuery.data.user.avatar.large)
                            .crossfade(500)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        filterQuality = FilterQuality.Low,
                        contentScale = ContentScale.Crop,
                        loading = { AnimatedShimmer(24.dp, 24.dp) }
                    )

                    Text(
                        text = userByQuery.data.user.name,
                        style = mTypography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            } else {
                ErrorSection(
                    errorText = userByQuery.exception.toString(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}