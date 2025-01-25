package com.example.settingsscreen.settings_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
) = composable<SettingsScreenRoute>(
    enterTransition = { fadeIn(tween((400))) },
    exitTransition = { fadeOut(tween(400)) }
) {
    SettingsScreen(
        navController = navController,
        viewModel = appSettingsVM
    )
}