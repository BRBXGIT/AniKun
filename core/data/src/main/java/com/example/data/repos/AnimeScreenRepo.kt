package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.anime_models.anime_list_response.Media as AnimeListMedia
import kotlinx.coroutines.flow.Flow

interface AnimeScreenRepo {

    fun getAnimeList(
        sort: String,
        season: String?,
        seasonYear: Int?
    ): Flow<PagingData<AnimeListMedia>>
}