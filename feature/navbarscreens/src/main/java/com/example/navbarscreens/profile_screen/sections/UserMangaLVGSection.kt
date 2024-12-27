package com.example.navbarscreens.profile_screen.sections

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
import com.example.designsystem.media_cards.MangaCard
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.data.remote.models.profile_models.user_manga_list_response.Entry as MangaListEntry

@Composable
fun UserMangaLVGSection(
    mangaList: List<MangaListEntry>,
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
        itemsIndexed(mangaList) { index, manga ->
            MangaCard(
                manga = manga.media,
                index = index,
                onMangaClick = {
                    navController.navigate(
                        MediaDetailsScreenRoute(
                            mediaId = manga.media.id,
                            mediaType = manga.media.type
                        )
                    )
                }
            )
        }
    }
}