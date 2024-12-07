package com.example.navbarscreens.favorites_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia
import com.example.designsystem.manga_card.MangaCard
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute

@Composable
fun FavoriteMangaLVGSection(
    favoriteManga: List<MangaListMedia>,
    navController: NavController
) {
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
        itemsIndexed(favoriteManga) { index, manga ->
            MangaCard(
                manga = manga,
                index = index,
                onCardClick = {
                    navController.navigate(
                        MediaDetailsScreenRoute(
                            mediaId = manga.id,
                            mediaType = manga.type
                        )
                    )
                }
            )
        }
    }
}