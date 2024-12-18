package com.example.navbarscreens.favorites_screen.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.designsystem.icons.AniKunIcons
import com.example.designsystem.theme.mColors
import com.example.designsystem.theme.mShapes
import com.example.designsystem.theme.mTypography
import com.example.media_screen.media_screen.navigation.MediaDetailsScreenRoute
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import com.example.data.remote.models.manga_list_response.Media as MangaListMedia

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenSearchBar(
    navController: NavController,
    onSearchClick: (String) -> Unit,
    favoriteMangaByQuery: List<MangaListMedia>,
    favoriteAnimeByQuery: List<AnimeListMedia>,
    onExpandChange: () -> Unit,
    placeHolderText: String,
    chosenContentType: Boolean,
) {
    var query by rememberSaveable { mutableStateOf("") }

    SearchBar(
        inputField = {
            TextField(
                value = query,
                onValueChange = { query = it },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 1,
                placeholder = {
                    Text(text = placeHolderText)
                },
                leadingIcon = {
                    IconButton(
                        onClick = { onExpandChange() }
                    ) {
                        Icon(
                            painter = painterResource(id = AniKunIcons.NavigationArrowLeft),
                            contentDescription = null
                        )
                    }
                },
                trailingIcon = {
                    if(query.isNotEmpty()) {
                        IconButton(
                            onClick = { query = "" }
                        ) {
                            Icon(
                                painter = painterResource(id = AniKunIcons.BackspaceFilled),
                                contentDescription = null
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchClick(query) }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
            )
        },
        expanded = true,
        onExpandedChange = { onExpandChange() },
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if(!chosenContentType) {
                items(favoriteAnimeByQuery) { anime ->
                    SearchFavoriteItem(
                        onItemClick = {
                            navController.navigate(
                                MediaDetailsScreenRoute(
                                    mediaId = anime.id,
                                    mediaType = anime.type
                                )
                            )
                            onExpandChange()
                        },
                        modifier = Modifier.animateItem(),
                        animeMedia = anime
                    )
                }
            } else {
                items(favoriteMangaByQuery) { manga ->
                    SearchFavoriteItem(
                        onItemClick = {
                            navController.navigate(
                                MediaDetailsScreenRoute(
                                    mediaId = manga.id,
                                    mediaType = manga.type
                                )
                            )
                            onExpandChange()
                        },
                        modifier = Modifier.animateItem(),
                        mangaMedia = manga
                    )
                }
            }
        }
    }
}

@Composable
fun SearchFavoriteItem(
    onItemClick: () -> Unit,
    mangaMedia: MangaListMedia? = null,
    animeMedia: AnimeListMedia? = null,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(mShapes.small)
            .clickable { onItemClick() }
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = AniKunIcons.Magnifier),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            if(animeMedia != null) {
                Text(
                    text = if(animeMedia.title.english == null) animeMedia.title.romaji else animeMedia.title.english!!,
                    style = mTypography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                val genres = animeMedia.genres.take(3).toString().replace("[", "").replace("]", "")
                val score = "${animeMedia.averageScore.toString().take(1)}.${animeMedia.averageScore.toString().takeLast(1)}"
                Text(
                    text = "$score • ${if(genres == "") "Unspecified" else genres}",
                    style = mTypography.labelMedium.copy(
                        color = mColors.secondary
                    )
                )
            }
            if(mangaMedia != null) {
                Text(
                    text = if(mangaMedia.title.english == null) mangaMedia.title.romaji else mangaMedia.title.english!!,
                    style = mTypography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                val genres = mangaMedia.genres.take(3).toString().replace("[", "").replace("]", "")
                val score = "${mangaMedia.averageScore.toString().take(1)}.${mangaMedia.averageScore.toString().takeLast(1)}"
                Text(
                    text = "$score • ${if(genres == "") "Unspecified" else genres}",
                    style = mTypography.labelMedium.copy(
                        color = mColors.secondary
                    )
                )
            }
        }
    }
}