package com.example.settingsscreen.settings_screen.sections

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.designsystem.icons.AniKunIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenTopBar(
    onBackIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Settings"
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackIconClick() }
            ) {
                Icon(
                    painter = painterResource(id = AniKunIcons.ArrowLeftFilled),
                    contentDescription = null
                )
            }
        }
    )
}