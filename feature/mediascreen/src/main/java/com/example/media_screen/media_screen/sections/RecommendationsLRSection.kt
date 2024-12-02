package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.remote.models.media_details_models.media_details_response.Recommendations
import com.example.designsystem.recommendation_card.RecommendationCard
import com.example.designsystem.theme.mTypography

@Composable
fun RecommendationsLRSection(
    recommendations: Recommendations,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "Recommendations",
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp)
        )

        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(recommendations.nodes) { recommendation ->
                RecommendationCard(recommendation)
            }
        }
    }
}