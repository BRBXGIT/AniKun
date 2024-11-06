package com.example.navbarscreens.anime_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.remote.repos.AnimeScreenRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeScreenVM @Inject constructor(
    repository: AnimeScreenRepoImpl
): ViewModel() {
    val trendingAnime = repository.getAnimeList("TRENDING_DESC").cachedIn(viewModelScope)

}