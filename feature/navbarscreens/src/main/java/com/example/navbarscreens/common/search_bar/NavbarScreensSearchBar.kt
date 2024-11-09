package com.example.navbarscreens.common.search_bar

import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.designsystem.error_section.ErrorSection
import com.example.designsystem.icons.AniListIcons
import com.example.navbarscreens.anime_screen.screen.AnimeScreenVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavbarScreensSearchBar(
    viewModel: AnimeScreenVM,
    onExpandChange: () -> Unit,
    placeHolderText: String
) {
    var query by rememberSaveable { mutableStateOf("") }
    val animeByQuery = viewModel.animeByQuery.collectAsLazyPagingItems()

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
                            painter = painterResource(id = AniListIcons.ArrowLeftFilled),
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
                                painter = painterResource(id = AniListIcons.BackspaceFilled),
                                contentDescription = null
                            )
                        }
                    }
                },
                keyboardActions = KeyboardActions(
                    onSearch = { viewModel.setQuery(query) }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
            )
        },
        expanded = true,
        onExpandedChange = { onExpandChange() },
    ) {
        var errorText by rememberSaveable { mutableStateOf("") }
        LaunchedEffect(animeByQuery.loadState.refresh) {
            if(animeByQuery.loadState.refresh is LoadState.Error) {
                errorText = (animeByQuery.loadState.refresh as LoadState.Error).error.message.toString()
            }
        }

        LazyColumn {
            if(errorText.isBlank()) {
                items(animeByQuery.itemCount) { index ->
                    val currentAnime = animeByQuery[index]

                    currentAnime?.let {
                        SearchItem(
                            onExpandChange = { onExpandChange() },
                            anime = currentAnime
                        )
                    }
                }
            } else {
                item {
                    ErrorSection(
                        errorText = errorText,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }
    }
}