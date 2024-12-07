package com.example.media_screen.media_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.data.remote.models.media_details_models.media_details_response.TitleX
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreenTopBar(
    isInList: Boolean,
    isFavorite: Boolean,
    title: TitleX?,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavIconClick: () -> Unit,
    onListIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit
) {
    var mediaTitle by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(title) {
        if(title != null) {
            mediaTitle = if(title.english == null) title.romaji else title.english!!
        }
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mColors.surfaceContainer.copy(alpha = 0f)
        ),
        title = {
            AnimatedVisibility(
                visible = scrollBehavior.state.contentOffset <= -600f,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(300))
            ) {
                Text(
                    text = mediaTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onNavIconClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.ArrowLeftFilled),
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(
                        id = if(isInList) AniKunIcons.InListFilled else AniKunIcons.List
                    ),
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { onFavoriteIconClick() }
            ) {
                Icon(
                    painter = painterResource(
                        id = if(isFavorite) AniKunIcons.HeartFilled else AniKunIcons.Heart
                    ),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}