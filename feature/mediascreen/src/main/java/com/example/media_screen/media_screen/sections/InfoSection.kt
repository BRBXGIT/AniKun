package com.example.media_screen.media_screen.sections

import android.icu.text.IDNA.Info
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.remote.models.media_details_response.EndDate
import com.example.data.remote.models.media_details_response.StartDate
import com.example.data.remote.models.media_details_response.TitleX
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mTypography

@Composable
fun InfoSection(
    title: TitleX,
    format: String,
    episodes: Int,
    episodeDuration: Int,
    source: String,
    status: String,
    startDate: StartDate,
    endDate: EndDate,
    season: String,
    seasonYear: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Info",
            style = mTypography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow("Romaji", title.romaji, true)
            if(title.english != null) {
                InfoRow("English", title.english!!, true)
            }
            if(title.native != null) {
                InfoRow("Native", title.native!!, true)
            }
        }

        HorizontalDivider(thickness = 1.dp)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow("Format", format)
            InfoRow("Episodes", episodes.toString())
            InfoRow("Episode duration", episodeDuration.toString())
            InfoRow("Source", source)
            InfoRow("Status", status)
            InfoRow("Start date", "${formatDate(startDate.day.toString())}.${startDate.month}.${startDate.year}")
            InfoRow("End date", "${formatDate(endDate.day.toString())}.${endDate.month}.${endDate.year}")
            InfoRow("Season", "$season $seasonYear")
        }
    }
}

@Composable
private fun InfoRow(
    infoType: String,
    info: String,
    useTint: Boolean = false
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = infoType,
            style = mTypography.labelLarge
        )

        Text(
            text = info,
            style = mTypography.labelLarge.copy(
                color = if(useTint) mColors.primary else mColors.onBackground,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun formatDate(date: String): String {
    val formattedDate: String
    if(date.length == 1) {
        formattedDate = "0$date"
        return formattedDate
    } else {
        return date
    }
}