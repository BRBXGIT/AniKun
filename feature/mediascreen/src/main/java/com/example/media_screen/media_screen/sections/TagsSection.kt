package com.example.media_screen.media_screen.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.remote.models.media_details_models.media_details_response.Tag
import com.example.designsystem.custom_modifiers.noRippleClickable
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mTypography

@Composable
fun TagsSection(
    tags: List<Tag>
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val notExpandedTags = tags.take(5)

    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .animateContentSize()
    ) {
        Text(
            text = "Tags",
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        if(!expanded) {
            notExpandedTags.forEach { tag ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = tag.name,
                        style = mTypography.labelLarge
                    )

                    Text(
                        text = "${tag.rank}%",
                        style = mTypography.labelLarge
                    )
                }
            }
        } else {
            tags.forEach { tag ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = tag.name,
                        style = mTypography.labelLarge
                    )

                    Text(
                        text = "${tag.rank}%",
                        style = mTypography.labelLarge
                    )
                }
            }
        }

        Icon(
            painter = painterResource(id = if(expanded) AniKunIcons.ArrowUp else AniKunIcons.ArrowDown),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .noRippleClickable {
                    expanded = !expanded
                }
        )
    }
}