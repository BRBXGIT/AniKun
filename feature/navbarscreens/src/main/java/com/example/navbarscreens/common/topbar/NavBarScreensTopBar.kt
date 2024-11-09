package com.example.navbarscreens.common.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.designsystem.icons.AniListIcons
import com.example.designsystem.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarScreensTopBar(
    text: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = mColors.surface
        ),
        title = { Text(text) },
        actions = {
            IconButton(
                onClick = { onSearchClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniListIcons.Magnifier),
                    contentDescription = null
                )
            }

            IconButton(
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(id = AniListIcons.Settings),
                    contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}