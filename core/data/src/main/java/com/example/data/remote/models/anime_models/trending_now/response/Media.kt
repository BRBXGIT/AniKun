package com.example.data.remote.models.anime_models.trending_now.response

data class Media(
    val averageScore: Int,
    val coverImage: CoverImage,
    val description: String,
    val genres: List<String>,
    val id: Int,
    val title: Title
)