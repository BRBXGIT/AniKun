package com.example.auth.navigation

import android.content.SharedPreferences
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.auth.screen.AuthScreen
import com.example.auth.screen.AuthScreenVM
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthScreenRoute(
    @SerialName("access_token")
    val accessToken: String? = null
)

fun NavGraphBuilder.authScreen(
    navController: NavController,
    prefs: SharedPreferences,
) = composable<AuthScreenRoute>(
    deepLinks = listOf(
        navDeepLink { uriPattern = "https://anikun-1e048.web.app/#{access_token}" }
    ),
    enterTransition = { fadeIn(tween(400)) },
    exitTransition = { fadeOut(tween(400)) }
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