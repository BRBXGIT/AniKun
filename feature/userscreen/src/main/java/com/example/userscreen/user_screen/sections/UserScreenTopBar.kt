package com.example.userscreen.user_screen.sections

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.designsystem.icons.AniKunIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenTopBar(
    onNavIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {  },
        navigationIcon = {
            IconButton(
                onClick = onNavIconClick
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.NavigationArrowLeft),
                    contentDescription = null
                )
            }
        }
    )
}