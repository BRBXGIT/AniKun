package com.example.data.remote.models.anime_models.trending_now.request

data class TrendingNowAnimeRequest(
    val query: String,
    val variables: Map<String, Any>
)
