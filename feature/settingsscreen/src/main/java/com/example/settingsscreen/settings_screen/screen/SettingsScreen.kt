package com.example.settingsscreen.settings_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.designsystem.theme.AppSettingsVM
import com.example.designsystem.theme.mColors
import com.example.settingsscreen.settings_screen.sections.ColorSystemElements
import com.example.settingsscreen.settings_screen.sections.SettingsScreenTopBar
import com.example.settingsscreen.settings_screen.sections.ThemePreviewsSection

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: AppSettingsVM
) {
    Scaffold(
        topBar = {
            SettingsScreenTopBar(
                onBackIconClick = { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val chosenTheme by viewModel.theme.collectAsState(initial = "default")
            val chosenColorSystem by viewModel.colorSystem.collectAsState(initial = "default")

            Spacer(modifier = Modifier.height(0.dp))

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
    }
}