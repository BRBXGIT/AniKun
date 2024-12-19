package com.example.media_screen.character_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.designsystem.icons.AniKunIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreenTopBar(
    isFavorite: Boolean,
    characterName: String?,
    onNavIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    var name by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(characterName) {
        if(characterName != null) {
            name = characterName
        }
    }

    TopAppBar(
        title = {
            AnimatedVisibility(
                visible = scrollBehavior.state.contentOffset <= -600f,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(300))
            ) {
                Text(
                    text = name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
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
        navigationIcon = {
            IconButton(
                onClick = { onNavIconClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.NavigationArrowLeft),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}