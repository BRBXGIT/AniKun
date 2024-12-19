package com.example.media_screen.character_screen.sections

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.designsystem.icons.AniKunIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreenTopBar(
    isFavorite: Boolean,
    onNavIconClick: () -> Unit,
    onFavoriteIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {  },
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