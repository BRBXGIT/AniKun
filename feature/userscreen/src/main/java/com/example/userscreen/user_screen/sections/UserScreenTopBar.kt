package com.example.userscreen.user_screen.sections

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenTopBar(
    onNavIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    userName: String?,
    containerScrollBehavior: TopAppBarScrollBehavior
) {
    var name by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(userName) {
        if(userName != null) {
            name = userName
        }
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mColors.background,
            scrolledContainerColor = mColors.background,
        ),
        scrollBehavior = scrollBehavior,
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = containerScrollBehavior.state.heightOffset == containerScrollBehavior.state.heightOffsetLimit,
                    enter = slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { it / 2 }
                    ) + fadeIn(tween(300)),
                    exit = slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { -it / 2 }
                    ) + fadeOut(tween(300))
                ) {
                    Text(text = "$name's favorites")
                }
                AnimatedVisibility(
                    visible = containerScrollBehavior.state.heightOffset != containerScrollBehavior.state.heightOffsetLimit,
                    enter = slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { it / 2 }
                    ) + fadeIn(tween(300)),
                    exit = slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { -it / 2 }
                    ) + fadeOut(tween(300))
                ) {
                    Text(text = name)
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavIconClick
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.NavigationArrowLeft),
                    contentDescription = null
                )
            }
        },
        actions = {
            val context = LocalContext.current
            IconButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "You found a secret :)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {

            }
        }
    )
}