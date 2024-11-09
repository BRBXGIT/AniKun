package com.example.navbarscreens.manga_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.date_functions.getDate
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.MangaScreenRepoImpl
import com.example.navbarscreens.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MangaScreenVM @Inject constructor(
    repository: MangaScreenRepoImpl,
    commonRepository: CommonRepoImpl
): ViewModel() {
    val trendingManga = repository.getMangaList(Utils.TRENDING_TYPE, null, null).cachedIn(viewModelScope)
    val allTimePopularManga = repository.getMangaList(Utils.POPULARITY_TYPE, null, null).cachedIn(viewModelScope)

    private val date = getDate()
    val thisSeasonAnime = repository.getMangaList(
        sort = Utils.POPULARITY_TYPE,
        season = date.season,
        seasonYear = date.year
    ).cachedIn(viewModelScope)
    val nextSeasonAnime = repository.getMangaList(
        sort = Utils.POPULARITY_TYPE,
        season = date.nextSeason,
        seasonYear = if(date.season == "FALL") date.nextYear else date.year
    ).cachedIn(viewModelScope)

    private val query = MutableStateFlow("")

    fun setQuery(searchBarQuery: String) {
        query.value = searchBarQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeByQuery = query
        .flatMapLatest { query ->
            commonRepository.getMediaByQuery(query, Utils.MANGA_TYPE).cachedIn(viewModelScope)
        }
}