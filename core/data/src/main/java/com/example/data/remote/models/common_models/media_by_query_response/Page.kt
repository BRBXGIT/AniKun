package com.example.data.remote.models.common_models.media_by_query_response

data class Page(
    val media: List<Media>,
    val pageInfo: HasNextPage
)