package com.example.data.remote.api_instance

import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.anime_models.anime_list_response.AnimeListResponse
import com.example.data.remote.models.common_models.media_by_query_response.MediaByQueryResponse
import com.example.data.remote.models.manga_models.manga_list_response.MangaListsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AniListApiInstance {

    @POST(".")
    suspend fun getAnimeList(
        @Body body: CommonRequest
    ): AnimeListResponse

    @POST(".")
    suspend fun getMangaList(
        @Body body: CommonRequest
    ): MangaListsResponse

    @POST(".")
    suspend fun getMediaByQuery(
        @Body body: CommonRequest
    ): MediaByQueryResponse
}