package com.example.data.repos

import androidx.paging.PagingData
import com.example.data.remote.models.profile_models.user_anime_list_response.Media as UserAnimeListMedia
import com.example.data.remote.models.profile_models.user_data.AniListUser
import com.example.data.remote.models.profile_models.user_manga_list_response.Media as UserMangaListMedia
import kotlinx.coroutines.flow.Flow

interface ProfileScreenRepo {

    suspend fun getAniListUser(accessToken: String): AniListUser

    suspend fun getUserAnimeList(
        accessToken: String,
        userName: String,
        status: String
    ): Flow<PagingData<UserAnimeListMedia>>

    suspend fun getUserMangaList(
        accessToken: String,
        userName: String,
        status: String
    ): Flow<PagingData<UserMangaListMedia>>
}