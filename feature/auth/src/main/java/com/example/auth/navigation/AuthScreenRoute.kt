package com.example.auth.navigation

import android.content.SharedPreferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.auth.screen.AuthScreen
import com.example.auth.screen.AuthScreenVM
import com.example.auth.utils.AuthUtils
import kotlinx.serialization.Serializable

@Serializable
data object AuthScreenRoute

fun NavGraphBuilder.authScreen(
    navController: NavController,
    prefs: SharedPreferences
) = composable<AuthScreenRoute> {
    val authScreenVM = hiltViewModel<AuthScreenVM>()

    AuthScreen(
        navController = navController,
        viewModel = authScreenVM,
        prefs = prefs
    )
}