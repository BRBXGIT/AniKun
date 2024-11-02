package com.example.data.remote.api_instance

import com.example.data.remote.models.anime_models.trending_now.request.TrendingNowAnimeRequest
import com.example.data.remote.models.anime_models.trending_now.response.TrendingNowAnimeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AniListApiInstance {

    @POST("graphql.anilist.co")
    suspend fun getTrendingAnime(
        @Body body: TrendingNowAnimeRequest
    ): TrendingNowAnimeResponse
}