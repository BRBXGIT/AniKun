package com.example.settingsscreen.settings_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.designsystem.theme.AppSettingsVM
import com.example.settingsscreen.settings_screen.screen.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen(
    appSettingsVM: AppSettingsVM,
    navController: NavController
) = composable<SettingsScreenRoute> {
    SettingsScreen(
        navController = navController,
        viewModel = appSettingsVM
    )
}