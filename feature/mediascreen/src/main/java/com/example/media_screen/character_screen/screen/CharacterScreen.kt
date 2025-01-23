package com.example.media_screen.character_screen.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.data.remote.models.profile_models.user_favorites_response.Favourites
import com.example.designsystem.sections.ErrorSection
import com.example.designsystem.theme.mColors
import com.example.media_screen.character_screen.sections.CharacterHeader
import com.example.media_screen.character_screen.sections.CharacterScreenTopBar
import com.example.media_screen.character_screen.sections.DescriptionSection
import com.example.media_screen.character_screen.sections.FavoritesCountSection
import com.example.media_screen.character_screen.sections.SeriesLRSection
import com.example.media_screen.media_screen.screen.MediaFavoritesScreensSharedVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterScreen(
    userFavorites: Favourites?,
    characterId: Int,
    navController: NavController,
    viewModel: CharacterScreenVM,
    favoritesScreenSharedVM: MediaFavoritesScreensSharedVM
) {
    //Get and collect character details
    val characterDetails = viewModel.characterDetails.collectAsStateWithLifecycle().value
    //Use LaunchedEffect to don't fetch data multiple times
    LaunchedEffect(characterDetails) {
        if((characterDetails.data == null) and (characterDetails.exception == null)) {
            viewModel.fetchCharacterDetails(characterId)
        }
    }

    var isCharacterInFavorites by rememberSaveable { mutableStateOf(false) }
    isCharacterInFavorites = if(userFavorites != null) {
        checkIsCharacterInFavorites(userFavorites, characterId)
    } else {
        false
    }

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        topBar = {
            CharacterScreenTopBar(
                onNavIconClick = { navController.navigateUp() },
                onFavoriteIconClick = {
                    favoritesScreenSharedVM.toggleFavorite("CHARACTER", characterId)
                },
                scrollBehavior = topBarScrollBehavior,
                isFavorite = isCharacterInFavorites,
                characterName = characterDetails.data?.character?.name?.full
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .background(mColors.background)
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if((characterDetails.data == null) && (characterDetails.exception == null)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if(characterDetails.exception != null) {
            ErrorSection(
                errorText = characterDetails.exception!!,
                modifier = Modifier.fillMaxSize()
            )
        }

        if(characterDetails.data != null) {
            characterDetails.data!!.character.let { character ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = innerPadding.calculateTopPadding())
                ) {
                    item {
                        CharacterHeader(
                            image = character.image.large,
                            name = character.name
                        )
                    }

                    item {
                        FavoritesCountSection(
                            favoritesCount = character.favourites,
                            mediaCount = character.media.nodes.size,
                            modifier = Modifier.fillParentMaxWidth()
                        )
                    }

                    if(character.description != null) {
                        item {
                            DescriptionSection(character.description!!)
                        }
                    }

                    item {
                        SeriesLRSection(
                            series = character.media.nodes,
                            navController = navController
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier
                                .height(0.dp)
                                .padding(innerPadding.calculateBottomPadding())
                        )
                    }
                }
            }
        }
    }
}

private fun checkIsCharacterInFavorites(
    userFavorites: Favourites,
    characterId: Int
): Boolean {
    userFavorites.characters.nodes.forEach { character ->
        if(character.id == characterId) return true
    }

    return false
}