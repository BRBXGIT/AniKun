package com.example.media_screen.media_screen.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mTypography

@Composable
fun DescriptionSection(
    description: String
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .animateContentSize()
    ) {
        if(!expanded) {
            // TODO create text blur
            Text(
                text = AnnotatedString.fromHtml(description),
                style = mTypography.bodyMedium,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        } else {
            Text(
                text = AnnotatedString.fromHtml(description),
                style = mTypography.bodyMedium
            )
        }

        Icon(
            painter = painterResource(id = if(expanded) AniKunIcons.ArrowUp else AniKunIcons.ArrowDown),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .noRippleClickable { expanded = !expanded }
        )
    }
}

//Custom modifier extension
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}