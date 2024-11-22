package com.example.navbarscreens.profile_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.AniKunIcons

@Composable
fun ContentTypeDDM(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onContentClick: (contentType: Boolean) -> Unit, //Anime -> 0, manga -> 1
    chosenContent: Boolean
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = {
                if(!chosenContent) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Show anime ")

                        Icon(
                            painter = painterResource(id = AniKunIcons.Check),
                            contentDescription = null
                        )
                    }
                } else {
                    Text(text = "Show anime ")
                }
            },
            onClick = {
                onDismissRequest()
                onContentClick(false)
            }
        )

        DropdownMenuItem(
            text = {
                if(chosenContent) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "Show manga")

                        Icon(
                            painter = painterResource(id = AniKunIcons.Check),
                            contentDescription = null
                        )
                    }
                } else {
                    Text(text = "Show manga")
                }
            },
            onClick = {
                onDismissRequest()
                onContentClick(true)
            }
        )
    }
}