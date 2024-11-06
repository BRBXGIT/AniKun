package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.anime_models.response.Media
import kotlinx.coroutines.flow.Flow

interface AnimeScreenRepo {

    fun getAnimeList(sort: String): Flow<PagingData<Media>>
}