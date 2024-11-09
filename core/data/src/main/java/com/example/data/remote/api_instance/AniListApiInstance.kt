package com.example.data.remote.api_instance

import com.example.data.remote.models.common_models.common_request.CommonRequest
import com.example.data.remote.models.anime_models.anime_list_response.AnimeListResponse
import com.example.data.remote.models.common_models.media_by_query_response.MediaByQueryResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AniListApiInstance {

    @POST(".")
    suspend fun getAnimeList(
        @Body body: CommonRequest
    ): AnimeListResponse

    @POST(".")
    suspend fun getMediaByQuery(
        @Body body: CommonRequest
    ): MediaByQueryResponse
}