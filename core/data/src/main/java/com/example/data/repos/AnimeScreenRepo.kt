package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.anime_models.trending_now.response.Media
import kotlinx.coroutines.flow.Flow

interface AnimeScreenRepo {

    suspend fun getTrendingAnime(): Flow<PagingData<Media>>
}