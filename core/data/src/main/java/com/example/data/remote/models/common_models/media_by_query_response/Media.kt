package com.example.data.remote.models.common_models.media_by_query_response

data class Media(
    val averageScore: Int,
    val genres: List<String>,
    val id: Int,
    val title: Title
)