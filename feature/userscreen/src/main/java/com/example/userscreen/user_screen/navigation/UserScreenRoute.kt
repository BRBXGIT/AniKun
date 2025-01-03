package com.example.userscreen.user_screen.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.userscreen.user_screen.screen.UserScreen
import kotlinx.serialization.Serializable

@Serializable
data class UserScreenRoute(
    val userName: String
)

fun NavGraphBuilder.userScreen() = composable<UserScreenRoute> {
    val userName = it.toRoute<UserScreenRoute>().userName

    UserScreen(userName)
}