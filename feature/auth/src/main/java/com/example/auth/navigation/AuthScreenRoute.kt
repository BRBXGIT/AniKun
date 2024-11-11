package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.auth.screen.AuthScreen
import com.example.auth.utils.AuthUtils
import kotlinx.serialization.Serializable

@Serializable
data class AuthScreenRoute(
    val accessToken: String = ""
)

fun NavGraphBuilder.authScreenRoute(
    navController: NavController
) = composable<AuthScreenRoute>(
    deepLinks = listOf(
        navDeepLink<AuthScreenRoute>(basePath = "https://anilist.co/api/v2/oauth/authorize?client_id=${AuthUtils.CLIENT_ID}&response_type=token")
    )
) {
    val accessToken = it.toRoute<AuthScreenRoute>().accessToken

    AuthScreen(
        navController = navController,
        accessToken = accessToken
    )
}