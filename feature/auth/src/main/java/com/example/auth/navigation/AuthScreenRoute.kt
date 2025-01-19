package com.example.auth.navigation

import android.content.SharedPreferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.auth.screen.AuthScreen
import com.example.auth.screen.AuthScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class AuthScreenRoute(
    val accessToken: String = ""
)

fun NavGraphBuilder.authScreen(
    navController: NavController,
    prefs: SharedPreferences,
) = composable<AuthScreenRoute>(
    deepLinks = listOf(
        navDeepLink<AuthScreenRoute>(basePath = "https://anilist.co/api/v2/oauth/accessToken")
    )
) {
    val authScreenVM = hiltViewModel<AuthScreenVM>()
    val accessToken = it.toRoute<AuthScreenRoute>().accessToken

    AuthScreen(
        navController = navController,
        viewModel = authScreenVM,
        prefs = prefs,
        accessToken = accessToken
    )
}