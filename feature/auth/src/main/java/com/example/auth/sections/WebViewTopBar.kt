package com.example.auth.sections

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewTopBar(
    onCloseClick: () -> Unit,
    progress: Int
) {
    TopAppBar(
        title = {
            Text(
                text = if(progress < 100) {
                    "Trying to load AniList"
                } else {
                    "Now you can authenticate :)"
                },
                maxLines = 1,
                style = mTypography.bodyLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onCloseClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.CrossCircle),
                    contentDescription = null
                )
            }
        }
    )
}