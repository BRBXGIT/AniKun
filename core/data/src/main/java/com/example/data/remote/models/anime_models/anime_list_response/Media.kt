package com.example.data.remote.models.anime_models.anime_list_response

data class Media(
    val averageScore: Int,
    val coverImage: CoverImage,
    val description: String?,
    val genres: List<String>,
    val id: Int,
    val episodes: Int,
    val title: Title
)