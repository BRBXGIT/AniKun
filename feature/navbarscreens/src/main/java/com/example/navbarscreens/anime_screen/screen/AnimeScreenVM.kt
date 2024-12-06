package com.example.navbarscreens.anime_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.remote.repos.AnimeScreenRepoImpl
import com.example.data.remote.repos.CommonRepoImpl
import com.example.navbarscreens.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class AnimeScreenVM @Inject constructor(
    repository: AnimeScreenRepoImpl,
    commonRepository: CommonRepoImpl
): ViewModel() {
    val trendingAnime = repository.getAnimeList().cachedIn(viewModelScope)

    private val query = MutableStateFlow("")

    fun setQuery(searchBarQuery: String) {
        query.value = searchBarQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeByQuery = query
        .flatMapLatest { query ->
            commonRepository.getMediaByQuery(query, Utils.ANIME_TYPE).cachedIn(viewModelScope)
        }
}