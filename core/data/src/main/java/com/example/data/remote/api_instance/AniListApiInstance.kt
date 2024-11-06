package com.example.data.remote.api_instance

import com.example.data.remote.models.anime_models.request.TrendingNowAnimeRequest
import com.example.data.remote.models.anime_models.response.TrendingNowAnimeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AniListApiInstance {

    @POST(".")
    suspend fun getAnime(
        @Body body: TrendingNowAnimeRequest
    ): TrendingNowAnimeResponse
}