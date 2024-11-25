package com.example.data.remote.models.anime_list_response

data class Page(
    val media: List<Media>,
    val pageInfo: HasNextPage
)