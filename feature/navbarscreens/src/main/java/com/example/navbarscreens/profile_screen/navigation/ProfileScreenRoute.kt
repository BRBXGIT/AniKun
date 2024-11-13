package com.example.navbarscreens.profile_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navbarscreens.profile_screen.screen.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileScreenRoute

fun NavGraphBuilder.profileScreen(
    navController: NavController
) = composable<ProfileScreenRoute> {
    ProfileScreen(navController)
}