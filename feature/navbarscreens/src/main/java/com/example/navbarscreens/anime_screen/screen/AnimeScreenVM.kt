package com.example.navbarscreens.anime_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.common.date_functions.getDate
import com.example.data.remote.repos.AnimeScreenRepoImpl
import com.example.navbarscreens.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeScreenVM @Inject constructor(
    repository: AnimeScreenRepoImpl
): ViewModel() {
    val trendingAnime = repository.getAnimeList(Utils.TRENDING_TYPE, null, null).cachedIn(viewModelScope)
    val allTimePopularAnime = repository.getAnimeList(Utils.POPULARITY_TYPE, null, null).cachedIn(viewModelScope)

    private val date = getDate()
    val thisSeasonAnime = repository.getAnimeList(
        sort = Utils.POPULARITY_TYPE,
        season = date.season,
        seasonYear = date.year
    ).cachedIn(viewModelScope)
    val nextSeasonAnime = repository.getAnimeList(
        sort = Utils.POPULARITY_TYPE,
        season = date.nextSeason,
        seasonYear = if(date.season == "FALL") date.nextYear else date.year
    ).cachedIn(viewModelScope)
}