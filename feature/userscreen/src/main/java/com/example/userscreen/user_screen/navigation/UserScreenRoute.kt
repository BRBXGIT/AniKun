package com.example.userscreen.user_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.userscreen.user_screen.screen.UserScreen
import com.example.userscreen.user_screen.screen.UserScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class UserScreenRoute(
    val userName: String
)

fun NavGraphBuilder.userScreen(
    navController: NavController
) = composable<UserScreenRoute> {
    val userName = it.toRoute<UserScreenRoute>().userName
    val userScreenVM = hiltViewModel<UserScreenVM>()

    UserScreen(
        userName = userName,
        viewModel = userScreenVM,
        navController = navController
    )
}