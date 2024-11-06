package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.anime_models.response.Media
import kotlinx.coroutines.flow.Flow

interface AnimeScreenRepo {

    fun getAnime(sort: String): Flow<PagingData<Media>>
}