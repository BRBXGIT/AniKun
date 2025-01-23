package com.example.userscreen.user_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_favorites_response.Node
import com.example.designsystem.media_cards.CharacterCard
import com.example.designsystem.sections.EmptyContentSection
import com.example.media_screen.character_screen.navigation.CharacterScreenRoute

@Composable
fun UserFavoriteCharacterLVGSection(
    userName: String,
    favoriteCharacters: List<Node>,
    navController: NavController,
    bottomPadding: Dp
) {
    if(favoriteCharacters.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(favoriteCharacters) { index, character ->
                CharacterCard(
                    character = character,
                    index = index,
                    onCardClick = {
                        navController.navigate(CharacterScreenRoute(character.id))
                    }
                )
            }

            //Use two items cause of grid
            item {
                Spacer(
                    modifier = Modifier.height(bottomPadding)
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(bottomPadding)
                )
            }
        }
    } else {
        EmptyContentSection(
            text = "Hmm, $userName doesn't have any favorite character",
            modifier = Modifier.fillMaxSize()
        )
    }
}