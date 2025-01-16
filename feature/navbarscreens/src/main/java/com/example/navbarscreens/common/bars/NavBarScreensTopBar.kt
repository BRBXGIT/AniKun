package com.example.navbarscreens.common.bars

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
import com.example.designsystem.media_cards.AnimatedShimmer
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.navbarscreens.profile_screen.sections.ContentTypeDDM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBarScreensTopBar(
    text: String? = null,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    userAvatar: String? = null, //Only for profile screen
    userName: String? = null, //Only for profile screen
    chosenContent: Boolean? = null, //Only for profile screen
    onContentClick: (contentType: Boolean) -> Unit = {}, //Only for profile screen
    fromUserListsScreen: Boolean? = null //Only for favorites and profile screen
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = if(fromUserListsScreen != null) mColors.background else mColors.surfaceContainer
        ),
        title = {
            if(userName != null) {
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
                Text(text!!)
            }
        },
        actions = {
            IconButton(
                onClick = { onSearchClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.Magnifier),
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { onSettingsClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.Settings),
                    contentDescription = null
                )
            }

            if(chosenContent != null) {
                //ddm is dropDownMenu
                var ddmOpen by rememberSaveable { mutableStateOf(false) }

                IconButton(
                    onClick = { ddmOpen = true }
                ) {
                    Icon(
                        painter = painterResource(id = AniKunIcons.Menu),
                        contentDescription = null
                    )
                }

                ContentTypeDDM(
                    expanded = ddmOpen,
                    onDismissRequest = { ddmOpen = false },
                    onContentClick = { onContentClick(it) },
                    chosenContent = chosenContent
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}