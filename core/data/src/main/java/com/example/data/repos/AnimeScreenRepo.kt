package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.anime_list_response.Media as AnimeListMedia
import kotlinx.coroutines.flow.Flow

interface AnimeScreenRepo {

    fun getTrendingAnimeList(): Flow<PagingData<AnimeListMedia>>
}