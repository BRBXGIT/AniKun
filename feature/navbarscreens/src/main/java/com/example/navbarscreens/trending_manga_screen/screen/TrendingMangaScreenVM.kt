package com.example.navbarscreens.trending_manga_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.remote.repos.CommonRepoImpl
import com.example.data.remote.repos.MangaScreenRepoImpl
import com.example.navbarscreens.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TrendingMangaScreenVM @Inject constructor(
    repository: MangaScreenRepoImpl,
    commonRepository: CommonRepoImpl
): ViewModel() {
    val trendingManga = repository.getTrendingMangaList().cachedIn(viewModelScope)

    private val query = MutableStateFlow("")

    fun setQuery(searchBarQuery: String) {
        query.value = searchBarQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val mangaByQuery = query
        .flatMapLatest { query ->
            commonRepository.getMediaByQuery(query, Utils.MANGA_TYPE).cachedIn(viewModelScope)
        }
}