package com.example.data.remote.models.anime_models.trending_now.response

data class Page(
    val media: List<Media>,
    val pageInfo: HasNextPage
)