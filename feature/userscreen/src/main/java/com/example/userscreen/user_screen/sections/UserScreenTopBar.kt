package com.example.userscreen.user_screen.sections

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.media_cards.AnimatedShimmer
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreenTopBar(
    onNavIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    userName: String?,
    containerScrollBehavior: TopAppBarScrollBehavior,
    avatar: String?
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(avatar)
                                .crossfade(500)
                                .size(Size.ORIGINAL)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(26.dp, 26.dp)
                                .clip(mShapes.small),
                            filterQuality = FilterQuality.Low,
                            contentScale = ContentScale.Crop,
                            loading = { AnimatedShimmer(26.dp, 26.dp) }
                        )

                        Text(text = name)
                    }
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
        //Used cause of bug, the only solution i found
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