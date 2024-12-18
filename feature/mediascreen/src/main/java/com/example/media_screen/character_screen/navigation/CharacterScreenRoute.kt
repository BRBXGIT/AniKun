package com.example.media_screen.character_screen.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.media_screen.character_screen.screen.CharacterScreen
import com.example.media_screen.character_screen.screen.CharacterScreenVM
import kotlinx.serialization.Serializable

@Serializable
data class CharacterScreenRoute(
    val characterId: Int
)

fun NavGraphBuilder.characterScreen(
    navController: NavController
) = composable<CharacterScreenRoute> {
    val characterScreenVM = hiltViewModel<CharacterScreenVM>()
    val characterId = it.toRoute<CharacterScreenRoute>().characterId

    CharacterScreen(
        characterId = characterId,
        navController = navController,
        viewModel = characterScreenVM
    )
}