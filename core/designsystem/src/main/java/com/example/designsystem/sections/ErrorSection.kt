package com.example.designsystem.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.designsystem.R
import com.example.designsystem.theme.mTypography

@Composable
fun ErrorSection(
    errorText: String,
    modifier: Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_animation))
    Box(
        modifier = modifier.padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(170.dp),
                iterations = LottieConstants.IterateForever
            )

            Text(
                text = if(errorText == "HTTP 429 ") {
                    "${errorText}, please give AniList a little rest :), try in 2-3 minutes"
                } else {
                    errorText
                },
                style = mTypography.bodyMedium.copy(
                    lineBreak = LineBreak.Paragraph
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}