package com.example.settingsscreen.settings_screen.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.AppSettingsVM
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mTypography
import com.example.settingsscreen.settings_screen.sections.ColorSystemElements
import com.example.settingsscreen.settings_screen.sections.SettingsScreenTopBar
import com.example.settingsscreen.settings_screen.sections.ThemePreviewsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: AppSettingsVM
) {
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            SettingsScreenTopBar(
                onBackIconClick = { navController.navigateUp() },
                scrollBehavior = topBarScrollBehavior
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        val chosenTheme by viewModel.theme.collectAsState(initial = "default")
        val chosenColorSystem by viewModel.colorSystem.collectAsState(initial = "default")

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Text(
                text = "Theme",
                style = mTypography.labelLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ThemePreviewsSection(
                    viewModel = viewModel,
                    chosenTheme = chosenTheme
                )

                ColorSystemElements(
                    chosenTheme = chosenTheme,
                    onColorSystemClick = { viewModel.changeColorSystem(it) },
                    chosenColorSystem = chosenColorSystem
                )
            }

            Text(
                text = "Info",
                style = mTypography.labelLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 16.dp)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val context = LocalContext.current

                Column {
                    InfoBox("AniList", AniKunIcons.AniListColored, context, "https://anilist.co/user/BRBX")
                    InfoBox("GitHub", AniKunIcons.GitHubColored, context, "https://github.com/BRBXGIT")
                    InfoBox("Telegram", AniKunIcons.TelegramColored, context, "https://web.telegram.org/k/#@BRBX16")
                    InfoBox("Gmail", AniKunIcons.GmailColored, context, "", true)
                }

                Text(
                    textAlign = TextAlign.Center,
                    text = "If you found bug or you have wishes and ideas you can send it to me with this social networks :)",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = mTypography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(0.dp))
        }
    }
}

@Composable
private fun InfoBox(
    info: String,
    icon: Int,
    context: Context,
    link: String,
    gmail: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if(!gmail) {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(link)
                        )
                    )
                } else {
                    emailWithReceiver(
                        context = context,
                        emailReceiver = listOf("egorka2005sautkin@gmail.com")
                    )
                }
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified
            )

            Text(
                text = info,
                style = mTypography.labelMedium
            )
        }

        Icon(
            painter = painterResource(id = AniKunIcons.ArrowRight),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}

private fun emailWithReceiver(
    emailReceiver: List<String>,
    context: Context
) {
    val email = Intent(Intent.ACTION_SEND)
    email.type = "plain/text"
    email.putExtra(Intent.EXTRA_EMAIL, emailReceiver.toTypedArray())

    context.startActivity(Intent.createChooser(email, "Select app to continue"))
}