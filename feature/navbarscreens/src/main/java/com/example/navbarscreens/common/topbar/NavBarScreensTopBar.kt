package com.example.navbarscreens.common.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
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
import com.example.designsystem.animated_shimmer.AnimatedShimmer
import com.example.designsystem.icons.AniListIcons
import com.example.designsystem.theme.mColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarScreensTopBar(
    text: String = "",
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit,
    userAvatar: String = "",
    userName: String = ""
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = mColors.surface
        ),
        title = {
            if(userName != "") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userAvatar)
                            .crossfade(500)
                            .size(Size.ORIGINAL)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape),
                        filterQuality = FilterQuality.Low,
                        contentScale = ContentScale.Crop,
                        loading = { AnimatedShimmer(32.dp, 32.dp) }
                    )

                    Text(text = userName)
                }
            } else {
                Text(text)
            }
        },
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