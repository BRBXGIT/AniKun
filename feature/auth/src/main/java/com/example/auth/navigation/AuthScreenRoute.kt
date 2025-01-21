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
        navDeepLink { uriPattern = "http://ani-kun.com/#{access_token}" }
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