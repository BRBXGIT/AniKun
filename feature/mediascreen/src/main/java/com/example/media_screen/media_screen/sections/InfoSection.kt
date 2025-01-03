package com.example.media_screen.media_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.remote.models.media_details_models.media_details_response.EndDate
import com.example.data.remote.models.media_details_models.media_details_response.StartDate
import com.example.data.remote.models.media_details_models.media_details_response.Studios
import com.example.data.remote.models.media_details_models.media_details_response.TitleX
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography

@Composable
fun InfoSection(
    title: TitleX,
    format: String,
    episodes: Int,
    chapters: Int?,
    episodeDuration: Int,
    source: String?,
    status: String,
    startDate: StartDate,
    endDate: EndDate,
    season: String?,
    seasonYear: Int,
    studios: Studios
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

        val clipboardManager = LocalClipboardManager.current
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow("Romaji", title.romaji, true, clipboardManager)
            if(title.english != null) {
                InfoRow("English", title.english!!, true, clipboardManager)
            }
            if(title.native != null) {
                InfoRow("Native", title.native!!, true, clipboardManager)
            }
        }

        HorizontalDivider(thickness = 1.dp)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InfoRow("Format", format)
            if(format != "MANGA") {
                InfoRow("Episodes", episodes.toString())
            }
            if(format == "MANGA") {
                InfoRow("Chapters", chapters?.toString() ?: "?")
            }
            if(format != "MANGA") {
                InfoRow("Episode duration", episodeDuration.toString())
            }
            InfoRow("Source", source ?: "?")
            InfoRow("Status", status)
            InfoRow("Start date", "${formatDate(startDate.day.toString())}.${formatDate(startDate.month.toString())}.${startDate.year}")
            val formattedEndDate = "${formatDate(endDate.day.toString())}.${formatDate(endDate.month.toString())}.${endDate.year}"
            if(formattedEndDate == "00.00.0") {
                InfoRow("End date", "?")
            } else {
                InfoRow("End date", formattedEndDate)
            }
            if(season != null) {
                InfoRow("Season", "$season $seasonYear")
            }
        }

        HorizontalDivider(thickness = 1.dp)

        if(format != "MANGA") {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Studios",
                    style = mTypography.labelLarge
                )

                Column(
                    horizontalAlignment = Alignment.End,
                ) {
                    studios.nodes.forEach { studio ->
                        Text(
                            text = studio.name,
                            style = mTypography.labelLarge.copy(
                                color = mColors.primary
                            ),
                            textAlign = TextAlign.End,
                            softWrap = true,
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(mShapes.extraSmall)
                                .clickable {
                                    clipboardManager.setText(AnnotatedString(studio.name))
                                }
                                .padding(2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    infoType: String,
    info: String,
    useTint: Boolean = false,
    clipboardManager: ClipboardManager? = null
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = infoType,
            style = mTypography.labelLarge,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = info,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = mTypography.labelLarge.copy(
                color = if(useTint) mColors.primary else mColors.onBackground,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(start = 14.dp)
                .then(
                    if(useTint) {
                        Modifier
                            .clip(mShapes.extraSmall)
                            .clickable {
                                clipboardManager!!.setText(AnnotatedString(info))
                            }
                            .padding(2.dp)
                    } else {
                        Modifier.padding(2.dp)
                    }
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